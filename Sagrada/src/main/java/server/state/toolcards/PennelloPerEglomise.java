package server.state.toolcards;



import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import common.exceptions.InvalidMoveException;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.GameRules;

import java.util.*;

public class PennelloPerEglomise extends ToolCard {

    public PennelloPerEglomise(Model model){
        super(model);
    }

    @Override
    public void start(Player player) {
        expectedParameters = new ArrayDeque<>(4);
        parameters = new ArrayList<>(4);
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

}