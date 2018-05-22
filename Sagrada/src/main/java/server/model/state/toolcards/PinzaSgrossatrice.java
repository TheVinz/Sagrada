package server.model.state.toolcards;



import common.response.Response;
import server.model.state.utilities.Choice;
import server.model.state.boards.draftpool.DraftPoolCell;
import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.player.Player;

import java.util.*;

import static server.model.state.ModelObject.ModelType.*;

public class PinzaSgrossatrice extends ToolCard {
    public static final int INCREASE=0;
    public static final int DECREASE=1;

    public PinzaSgrossatrice(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 1;
    }

    public void start(Player player){
        expectedParameters = new ArrayDeque<>();
        parameters = new ArrayList<>();
        expectedParameters.add(DRAFT_POOL_CELL);  //la cella deve avere un dado, se no eccezione
        expectedParameters.add(CHOICE);
        this.player=player;
    }

    /*
    I doAbility terminano notificando alle view chi ha usato la carta
    TODO la notifica arriva solo dopo che l'operazione è stata compiuta
     */

    public void doAbility() throws InvalidMoveException {
        DraftPoolCell cell= (DraftPoolCell) parameters.get(0);
        Choice choice=(Choice) parameters.get(1);
        if(choice.getChoice()==INCREASE) model.increase(player, cell);
        else model.decrease(player, cell);
        model.toolCardUsed(player, this);
    }

    @Override
    public Response next() {   //scelta
        return super.next();
    }
}
