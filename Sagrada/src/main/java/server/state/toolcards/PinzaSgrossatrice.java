package server.state.toolcards;



import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.player.Player;

import java.util.*;
public class PinzaSgrossatrice implements ToolCard {
    public static final int INCREASE=0;
    public static final int DECREASE=1;

    private List<Object> parameters;
    private Queue<Class> expectedParameters;
    private Player player;
    private Model model;

    public PinzaSgrossatrice(Model model){
        this.model = model;
    }

    public void start(){
        expectedParameters = new ArrayDeque<>();
        parameters = new ArrayList<>();
        expectedParameters.add(DraftPoolCell.class);  //la cella deve avere un dado, se no eccezione
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

    /*
    I doAbility terminano notificando alle view chi ha usato la carta
    TODO la notifica arriva solo dopo che l'operazione è stata compiuta
     */

    public void doAbility() throws InvalidMoveException {
        DraftPoolCell cell= (DraftPoolCell) parameters.get(0);
        int choice=(Integer) parameters.get(1);
        if(choice==INCREASE) model.increase(player, cell);
        else model.decrease(player, cell);
        model.notifyToolCardUsed(player, this);
    }
}
