package TestGUI.server.model.toolcards;

import TestGUI.server.model.Model;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;
import TestGUI.server.model.dice.Dice;
import TestGUI.server.model.exceptions.InvalidMoveException;

import java.util.*;

public class PennelloperPastaSalda implements ToolCard {

    private List<Object> parameters;
    private Queue<Class> expectedParameters;
    private Model model;

    public PennelloperPastaSalda(Model model) {
        this.model=model;
    }

    public Class nextParameter() {
        return expectedParameters.poll();
    }

    @Override
    public void start() {
        expectedParameters = new ArrayDeque<Class>();
        parameters = new ArrayList<Object>();
        expectedParameters.add(WindowFrameCell.class);
        expectedParameters.add(Integer.class);
    }

    public boolean setParameter(Object o) throws InvalidMoveException {
        parameters.add(o);
        if(expectedParameters.isEmpty()){
            doAbility();
            return true;
        }
        else return false;
    }

    public void doAbility() throws InvalidMoveException {
        WindowFrameCell cell= (WindowFrameCell) parameters.get(0);
        if((Integer) parameters.get(1)==1) model.increaseDice(cell);
        else model.decreaseDice(cell);
    }

    @Override
    public String getName() {
        return "PennelloPerPastaSalda";
    }
}