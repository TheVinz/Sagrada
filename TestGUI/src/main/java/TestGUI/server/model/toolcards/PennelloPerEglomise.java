package TestGUI.server.model.toolcards;

import TestGUI.server.GameRules;
import TestGUI.server.model.Model;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;
import TestGUI.server.model.dice.Dice;
import TestGUI.server.model.exceptions.InvalidMoveException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PennelloPerEglomise implements ToolCard {
    private List<Object> parameters;
    private Queue<Class> expectedParameters;
    private Model model;

    public PennelloPerEglomise(Model model){
        this.model=model;
    }

    public void start(){
        expectedParameters = new ArrayDeque<>();
        parameters = new ArrayList<>();
        expectedParameters.add(WindowFrameCell.class);
        expectedParameters.add(WindowFrameCell.class);
    }

    public Class nextParameter(){
        return expectedParameters.poll();
    }

    public boolean setParameter(Object o) throws InvalidMoveException {
        parameters.add(o);
        if(expectedParameters.isEmpty()) {
            doAbility();
            return true;
        }
        else return false;
    }

    public void doAbility() throws InvalidMoveException {
        WindowFrameCell source = (WindowFrameCell) parameters.get(0);
        WindowFrameCell target = (WindowFrameCell) parameters.get(1);
        Dice dice= source.removeDice();
        if(!GameRules.validAdjacentDiceColors(model.getWindowFrame(), dice.getColor(), target)) {
            source.put(dice);
            throw new InvalidMoveException("Adjacent colors must be different.");
        }
        else if(!GameRules.validAdjacentDices(model.getWindowFrame(),target)){
            source.put(dice);
            throw new InvalidMoveException("Dice must have an another adjacent one.");
        }
        source.put(dice);
        model.move(source, target);
    }

    public String getName(){return "PennelloPerEglomise";}
}
