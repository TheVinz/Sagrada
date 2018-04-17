package common.toolcards;

import common.dice.Dice;
import common.exceptions.InvalidMoveException;

import java.util.*;

public class TamponeDiamantato implements ToolCard {

    private List<Object> parameters;
    private Queue<Class> expectedParameters;

    public TamponeDiamantato() {
        expectedParameters = new ArrayDeque<Class>();
        parameters = new ArrayList<Object>();
        expectedParameters.add(Dice.class);
    }

    public Class nextParameter() {
        return expectedParameters.peek();
    }

    public void setParameter(Object o) {
        parameters.add(o);
        expectedParameters.remove();
    }

    public void doAbility() throws InvalidMoveException {
        Dice dice = (Dice) parameters.get(0);
        dice.flip();
    }
}