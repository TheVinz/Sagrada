package server.state.toolcards;



import server.state.boards.windowframe.WindowFrameCell;
import common.exceptions.InvalidMoveException;
import server.Model;

import java.util.*;
public class PinzaSgrossatrice implements ToolCard {
    public static final int INCREASE=0;
    public static final int DECREASE=1;

    private List<Object> parameters;
    private Queue<Class> expectedParameters;
    private Model model;

    public PinzaSgrossatrice(Model model){
        this.model = model;
    }

    public void start(){
        expectedParameters = new ArrayDeque<>();
        parameters = new ArrayList<>();
        expectedParameters.add(WindowFrameCell.class);  //la cella deve avere un dado, se no eccezione
        expectedParameters.add(Integer.class);
    }

    @Override
    public boolean hasNext() {
        return !expectedParameters.isEmpty();
    }

    public void setParameter(Object o) throws InvalidMoveException {
        if(expectedParameters.poll()!=o.getClass()) throw new InvalidMoveException("Invalid parameter");
        parameters.add(o);
        if(!hasNext()) doAbility();
    }
    public void doAbility() throws InvalidMoveException {
        WindowFrameCell cell= (WindowFrameCell) parameters.get(0);
        int choice=(Integer) parameters.get(1);
        if(choice==INCREASE) cell.getDice().increase();
        else cell.getDice().decrease();
    }
}
