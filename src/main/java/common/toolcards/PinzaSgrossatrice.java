package common.toolcards;

import common.dice.Dice;
import common.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class PinzaSgrossatrice implements ToolCard {

    private List<Object> parameters;
    private Stack<Class> expectedParameters;

    public PinzaSgrossatrice(){
        expectedParameters = new Stack<Class>();
        parameters = new ArrayList<Object>();
        expectedParameters.add(Dice.class);
        expectedParameters.add(Integer.class);
    }

    public Class nextParameter() throws EmptyStackException {
        return expectedParameters.peek();
    }

    public void setParameters(Object o)
    {
        parameters.add(o);
        expectedParameters.pop();
    }
    public void doAbility() throws InvalidMoveException {
        Dice dice = (Dice) parameters.get(0);
        Integer choice = (Integer) parameters.get(1);
        if(choice.equals(0))
            dice.increase();
        else
            dice.decrease();
    }
}
