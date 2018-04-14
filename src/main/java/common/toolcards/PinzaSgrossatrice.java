package common.toolcards;

import common.dice.Dice;
import common.exceptions.InvalidMoveException;

import java.util.*;

public class PinzaSgrossatrice implements ToolCard {

    private List<Object> parameters;
    private Queue<Class> expectedParameters;

    public PinzaSgrossatrice(){
        expectedParameters = new ArrayDeque<Class>();
        parameters = new ArrayList<Object>();
        expectedParameters.add(Dice.class);
        expectedParameters.add(Integer.class);
    }

    public Class nextParameter(){
        return expectedParameters.peek();
    }

    public void setParameter(Object o)
    {
        parameters.add(o);
        expectedParameters.remove();
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