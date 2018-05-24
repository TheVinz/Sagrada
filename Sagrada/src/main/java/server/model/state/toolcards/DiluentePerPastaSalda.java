package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.utilities.Choice;
import server.model.state.utilities.GameRules;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

@SuppressWarnings("Duplicates")
public class DiluentePerPastaSalda extends ToolCard {

    private boolean valueSetted;
    private boolean playable;
    private boolean drawDone;
    private DraftPoolCell poolCell;
    private Dice dice;

    public DiluentePerPastaSalda(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 11;
    }

    @Override
    public void start(Player player) {
        expectedParameters=new ArrayDeque<>(3);
        parameters=new ArrayList<>(3);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        this.player=player;
        playable=false;
        drawDone=false;
        valueSetted=false;
    }

    @Override
    public void setParameter(ModelObject o) throws InvalidMoveException {
        if(!drawDone) {
            if(o.getType()!=DRAFT_POOL_CELL) throw new InvalidMoveException("Wrong parameter");
            else {
                parameters.add(o);
                doAbility();
            }
        }
        else if(!valueSetted){
            if(o.getType()==CHOICE) {
                parameters.add(o);
                doAbility();
            }
        }
        else {
            if(o.getType()==expectedParameters.peek()){
                expectedParameters.poll();
                parameters.add(o);
                if(expectedParameters.isEmpty()) doAbility();
            }
        }
    }


    @Override
    void doAbility() throws InvalidMoveException {
        if(!drawDone) {
            poolCell = (DraftPoolCell) parameters.get(0);
            dice = poolCell.removeDice();
            model.getState().getBag().insert(dice);
            dice=model.drawDice(player);
            drawDone=true;
        }
        else if(!valueSetted){
            dice=new Dice(dice.getColor(), ((Choice) parameters.get(1)).getChoice());
            model.putDice(player, dice, poolCell);
            valueSetted=true;
            playable=verify(dice);
        }
        else{
            WindowFrame frame= (WindowFrame) parameters.get(2);
            WindowFrameCell cell= (WindowFrameCell) parameters.get(3);
            try {
                if (frame != player.getWindowFrame() || !GameRules.validAllCellRestriction(poolCell.getDice(), cell)
                        || !GameRules.validAllDiceRestriction(frame, poolCell.getDice(), cell)) {
                    parameters.remove(frame);
                    parameters.remove(cell);
                    expectedParameters.add(WINDOW_FRAME);
                    expectedParameters.add(WINDOW_FRAME_CELL);
                } else {
                    model.move(player, poolCell, cell);
                    playable=false;
                    model.toolCardUsed(player, this);
                }
            }
            catch (InvalidMoveException e){
                parameters.remove(frame);
                parameters.remove(cell);
                expectedParameters.add(WINDOW_FRAME);
                expectedParameters.add(WINDOW_FRAME_CELL);
            }
        }
    }

    @Override
    public boolean hasNext(){
        return drawDone && playable;
    }

    private boolean verify(Dice dice) {
        for(int i=0; i<WindowFrame.ROWS; i++) {
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if (GameRules.validAllDiceRestriction(player.getWindowFrame(), dice, player.getWindowFrame().getCell(i,j))
                        && GameRules.validAllCellRestriction(dice, player.getWindowFrame().getCell(i,j)))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Response next() {  //scelta
        if(expectedParameters.peek().equals(CHOICE))
            return Response.DILUENTE_PER_PASTA_SALDA_CHOICE;
        else if(!drawDone)
            return Response.DRAFT_POOL_CELL;
        else
            return super.next();
    }

}
