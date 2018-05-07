package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.boards.roundtrack.RoundTrackCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.Choice;
import server.state.utilities.Color;
import server.state.utilities.GameRules;

import java.util.ArrayDeque;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class TaglierinaManuale extends ToolCard {

    public static final int TWO_MOVES=0;
    public static final int ONE_MOVE=1;

    public TaglierinaManuale(Model model) {
        super(model);
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        expectedParameters=new ArrayDeque<>(10);
        parameters=new ArrayList<>(10);
        expectedParameters.add("RoundTrackCell");
        expectedParameters.add("WindowFrame");
        expectedParameters.add("WindowFrameCell");
        expectedParameters.add("WindowFrame");
        expectedParameters.add("WindowFrameCell");
        expectedParameters.add("Choice");
        expectedParameters.add("WindowFrame");
        expectedParameters.add("WindowFrameCell");
        expectedParameters.add("WindowFrame");
        expectedParameters.add("WindowFrameCell");
        this.player=player;
    }

    @Override
    public boolean hasNext(){
        if(parameters.get(5)!=null && parameters.get(5).equals(ONE_MOVE)) return false;
        else return !expectedParameters.isEmpty();
    }

    @Override
    void doAbility() throws InvalidMoveException {
        RoundTrackCell roundTrackCell= (RoundTrackCell) parameters.get(0);
        WindowFrame firstSourceFrame= (WindowFrame) parameters.get(1);
        WindowFrameCell firstSource= (WindowFrameCell) parameters.get(2);
        WindowFrame firstTargetFrame= (WindowFrame) parameters.get(3);
        WindowFrameCell firstTarget= (WindowFrameCell) parameters.get(4);
        Integer secondMove=((Choice) parameters.get(5)).getChoice();
        WindowFrame secondSourceFrame= (WindowFrame) parameters.get(6);
        WindowFrameCell secondSource= (WindowFrameCell) parameters.get(7);
        WindowFrame secondTargetFrame= (WindowFrame) parameters.get(8);
        WindowFrameCell secondTarget= (WindowFrameCell) parameters.get(9);
        Color color=roundTrackCell.getDice().getColor();
        if(firstSourceFrame!=player.getWindowFrame() || firstTargetFrame!=player.getWindowFrame())
            throw new InvalidMoveException("Invalid frame");
        else if(firstSource.getDice().getColor()!=color)
            throw new InvalidMoveException("Dice colors error");
        else {
            Dice dice=firstSource.removeDice();
            if(!GameRules.validAllCellRestriction(firstSource.getDice(), firstTarget)
                    || !GameRules.validAllDiceRestriction(firstTargetFrame, firstSource.getDice(), firstSource)) {
                firstSource.put(dice);
                throw new InvalidMoveException("Move must respect all placement restrictions");
            }
        }
        if(secondMove==TWO_MOVES){
            secondSourceFrame= (WindowFrame) parameters.get(6);
            secondSource= (WindowFrameCell) parameters.get(7);
            secondTargetFrame= (WindowFrame) parameters.get(8);
            secondTarget= (WindowFrameCell) parameters.get(9);
            if(secondSourceFrame!=player.getWindowFrame() || secondTargetFrame!=player.getWindowFrame())
                throw new InvalidMoveException("Invalid frame");
            else if(secondSource.getDice().getColor()!=color)
                throw new InvalidMoveException("Dice colors error");
            else {
                Dice dice=secondSource.removeDice();
                if(!GameRules.validAllCellRestriction(secondSource.getDice(), secondTarget)
                        || !GameRules.validAllDiceRestriction(secondTargetFrame, secondSource.getDice(), secondSource)) {
                    secondSource.put(dice);
                    throw new InvalidMoveException("Move must respect all placement restrictions");
                }
            }
        }
        if(firstTarget.isEmpty()) {
            model.move(player, firstSource, firstTarget);
            if(secondMove==TWO_MOVES) {
                if(secondTarget.isEmpty()) {
                    model.move(player, secondSource, secondTarget);
                }
                else throw new InvalidMoveException("Already filled cell");
            }
        }
        else throw new InvalidMoveException("Already filled cell");
        model.toolCardUsed(player, this);
    }
}
