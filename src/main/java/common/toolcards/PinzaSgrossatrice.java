package common.toolcards;



import common.boards.windowframe.WindowFrameCell;
import common.dice.Dice;
import common.exceptions.InvalidMoveException;
import common.Player;
import server.Model;

import java.util.*;
public class PinzaSgrossatrice implements ToolCard {

    private List<Object> parameters;
    private Queue<Class> expectedParameters;
    private Model model;

    public PinzaSgrossatrice(Model model){
        this.model = model;
        expectedParameters = new ArrayDeque<Class>();
        parameters = new ArrayList<Object>();
        expectedParameters.add(Player.class);
        expectedParameters.add(WindowFrameCell.class);  //la cella deve avere un dado, se no eccezione
        expectedParameters.add(Integer.class);
    }

    public Class nextParameter(){
        return expectedParameters.peek();
    }  //da ritornare classe

    public void setParameter(Object o)
    {
        parameters.add(o);
        expectedParameters.remove();
    }
    public void doAbility() throws InvalidMoveException {
        model.pinzaSgrossatrice(parameters);


    }
}
