package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.utilities.GameRules;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

public class AlesatorePerLaminaDiRame extends ToolCard {

    public AlesatorePerLaminaDiRame(Model model){
        super(model);
    }

    @Override
    public int getNumber() {
        return 3;
    }

    @Override
    public void start(Player player) {
        parameters=new ArrayList<>(4);
        expectedParameters=new ArrayDeque<>(4);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        this.player=player;
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
        else if(source.isEmpty()) throw new InvalidMoveException("Empty cell");
        else{
            Dice dice=source.removeDice();
            if(!GameRules.validCellColor(dice, target)) {
                source.put(dice);
                throw new InvalidMoveException("Cell and dice colors must be equal");
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
        if(expectedParameters.peek().equals(WINDOW_FRAME) && expectedParameters.size()==4)
            return Response.WINDOW_FRAME_MOVE;
        else
            return null;
    }
}
