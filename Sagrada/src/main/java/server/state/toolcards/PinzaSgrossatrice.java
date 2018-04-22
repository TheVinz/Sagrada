package server.state.toolcards;



import server.state.boards.draftpool.DraftPoolCell;
import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.player.Player;

import java.util.*;
public class PinzaSgrossatrice extends ToolCard {
    public static final int INCREASE=0;
    public static final int DECREASE=1;

    public PinzaSgrossatrice(Model model) {
        super(model);
    }

    public void start(Player player){
        expectedParameters = new ArrayDeque<>();
        parameters = new ArrayList<>();
        expectedParameters.add(DraftPoolCell.class);  //la cella deve avere un dado, se no eccezione
        expectedParameters.add(Integer.class);
        this.player=player;
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
        model.toolCardUsed(player, this);
    }
}
