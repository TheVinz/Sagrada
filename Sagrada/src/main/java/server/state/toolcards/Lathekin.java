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

public class Lathekin extends ToolCard {

    public Lathekin(Model model) {
        super(model);
    }

    @Override
    public void start(Player player) {
        expectedParameters=new ArrayDeque<>(8);
        parameters=new ArrayList<>(8);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        this.player=player;
    }

    @Override
    void doAbility() throws InvalidMoveException {
        WindowFrame firstSourceFrame= (WindowFrame) parameters.get(0);
        WindowFrameCell firstSource= (WindowFrameCell) parameters.get(1);
        WindowFrame firstTargetFrame= (WindowFrame) parameters.get(2);
        WindowFrameCell firstTarget= (WindowFrameCell) parameters.get(3);
        WindowFrame secondSourceFrame= (WindowFrame) parameters.get(4);
        WindowFrameCell secondSource= (WindowFrameCell) parameters.get(5);
        WindowFrame secondtargetFrame= (WindowFrame) parameters.get(6);
        WindowFrameCell secondTarget= (WindowFrameCell) parameters.get(7);

        if(firstSource==secondSource)
            throw new InvalidMoveException("Choose two different dices");
        else if(firstTarget==secondTarget)
            throw new InvalidMoveException("Target positions must be different");
        else if(firstSourceFrame!=secondSourceFrame || firstSourceFrame!=firstTargetFrame
                || firstSourceFrame != secondtargetFrame || firstSourceFrame!=player.getWindowFrame())
            throw new InvalidMoveException("Dice must be on your same frame");
        else {
            Dice firstDice = firstSource.removeDice();
            Dice secondDice=secondSource.removeDice();
            if(!GameRules.validAllCellRestriction(firstDice, firstTarget)) {
                firstSource.put(firstDice);
                secondSource.put(secondDice);
                throw new InvalidMoveException("First move does not respect cell restrictions");
            }
            else if(!GameRules.validAllDiceRestriction(player.getWindowFrame(), firstDice, firstTarget)) {
                firstSource.put(firstDice);
                secondSource.put(secondDice);
                throw new InvalidMoveException("First move does not respect dice restrictions");
            }
            else if(!GameRules.validAllCellRestriction(secondDice, secondTarget)) {
                firstSource.put(firstDice);
                secondSource.put(secondDice);
                throw new InvalidMoveException("Second move does not respect cell restrictions");
            }
            else if(!GameRules.validAllDiceRestriction(player.getWindowFrame(), secondDice, secondTarget)) {
                firstSource.put(firstDice);
                secondSource.put(secondDice);
                throw new InvalidMoveException("Second move does not respect dice restrictions");
            }
            else{
                firstSource.put(firstDice);
                secondSource.put(secondDice);
                if(firstTarget.isEmpty() && secondTarget.isEmpty()) {
                    model.move(player, firstSource, firstTarget);
                    model.move(player, secondSource, secondTarget);
                    model.toolCardUsed(player, this);
                }
                else throw new InvalidMoveException("Already filled cell");
            }
        }
    }
}
