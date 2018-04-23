package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.GameRules;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class AlesatorePerLaminaDiRame extends ToolCard {

    public AlesatorePerLaminaDiRame(Model model){
        super(model);
    }

    @Override
    public void start(Player player) {
        parameters=new ArrayList<>(4);
        expectedParameters=new ArrayDeque<>(4);
        expectedParameters.add(WindowFrame.class);
        expectedParameters.add(WindowFrameCell.class);
        expectedParameters.add(WindowFrame.class);
        expectedParameters.add(WindowFrameCell.class);
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
}
