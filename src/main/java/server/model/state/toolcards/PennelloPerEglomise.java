package server.model.state.toolcards;



import common.response.Response;
import server.model.Model;
import server.model.state.utilities.Color;
import server.model.state.utilities.GameRules;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import common.exceptions.InvalidMoveException;
import server.model.state.dice.Dice;
import server.model.state.player.Player;

import java.util.*;

import static server.model.state.ModelObject.ModelType.*;

public class PennelloPerEglomise extends ToolCard {

    public PennelloPerEglomise(Model model){
        super(model);
    }

    @Override
    public int getNumber() {
        return 2;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        this.player=player;
        if(!playable())
            throw new InvalidMoveException("No available moves");
        expectedParameters = new ArrayDeque<>(4);
        parameters = new ArrayList<>(4);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void doAbility() throws InvalidMoveException {
        WindowFrame sourceFrame= (WindowFrame) parameters.get(0);
        WindowFrameCell source= (WindowFrameCell) parameters.get(1);
        WindowFrame targetFrame=(WindowFrame) parameters.get(2);
        WindowFrameCell target= (WindowFrameCell) parameters.get(3);
        if(sourceFrame != targetFrame || sourceFrame!=player.getWindowFrame())
            throw new InvalidMoveException("Wrong parameter");
        else{
            Dice dice=source.removeDice();
            if(!GameRules.validCellShade(dice, target)) {
                source.put(dice);
                throw new InvalidMoveException("Cell and dice shapes must be equal");
            }
            else if(!GameRules.validAllDiceRestriction(targetFrame, dice, target)) {
                source.put(dice);
                throw new InvalidMoveException("Invalid adjacent dices");
            }
            else{
                source.put(dice);
                model.move(player, source, target);
                model.toolCardUsed(player, this);
            }
        }
    }

    @Override
    public Response next() {   //trascinamento
        if (expectedParameters.peek().equals(WINDOW_FRAME) && expectedParameters.size() == 4)
            return Response.WINDOW_FRAME_MOVE;
        else
            return null;
    }

    private boolean playable(){
        for(int i=0 ; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++) {
                if (validateCell(player.getWindowFrame().getCell(i, j)))
                    return true;
            }
        }
        return false;
    }

    private boolean validateCell(WindowFrameCell cell){
        WindowFrame frame = player.getWindowFrame();
        WindowFrameCell frameCell;
        try {
            Dice dice = cell.removeDice();
            for(int i=0; i<WindowFrame.ROWS; i++){
                for(int j=0; j<WindowFrame.COLUMNS; j++){
                    frameCell = frame.getCell(i,j);
                    if(!frameCell.equals(cell) && frameCell.isEmpty() && GameRules.validAllDiceRestriction(frame, dice, frameCell)
                            && GameRules.validCellShade(dice, frameCell)){
                        cell.put(dice);
                        return true;
                    }
                }
            }
            cell.put(dice);
            return false;
        } catch (InvalidMoveException e) {
            return false;
        }
    }

}