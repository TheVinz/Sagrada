package server.model.state.toolcards;



import common.response.Response;
import server.model.state.utilities.Choice;
import server.model.state.boards.draftpool.DraftPoolCell;
import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.player.Player;
import server.model.state.utilities.Color;

import java.util.*;

import static server.model.state.ModelObject.ModelType.*;

/**
 * The <tt>PinzaSgrossatrice</tt> class represents the "Pinza sgrossatrice" {@link ToolCard}. The methods
 * in this class handles the ToolCard's effect:  After drafting, increase or decrease the value of the
 * drafted {@link server.model.state.dice.Dice} by 1.
 */
public class PinzaSgrossatrice extends ToolCard {
    public static final int INCREASE=0;
    public static final int DECREASE=1;

    /**
     * Initializes the ToolCard PinzaSgrossatrice.
     * @param model the model of this game.
     */
    public PinzaSgrossatrice(Model model) {
        super(model);
    }

    /**
     * Gets the number of the ToolCard.
     * @return 1 which represents the PinzaSgrossatrice ToolCard.
     */
    @Override
    public int getNumber() {
        return 1;
    }

    /**
     * Gets the {@link Color} of the ToolCard.
     * @return the Color PURPLE which represents the ToolCard's color.
     */
    @Override
    public Color getColor() {
        return Color.PURPLE;
    }

    /**
     * Initialize an array with the ToolCard's expected parameters.
     * @param player the {@link Player} whose using the ToolCard.
     * @throws InvalidMoveException if there aren't available moves.
     */
    public void start(Player player) throws InvalidMoveException {
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        expectedParameters = new ArrayDeque<>();
        parameters = new ArrayList<>();
        expectedParameters.add(DRAFT_POOL_CELL);
        expectedParameters.add(CHOICE);
        this.player=player;
    }


    void doAbility() throws InvalidMoveException {
        DraftPoolCell cell= (DraftPoolCell) parameters.get(0);
        if(cell.isEmpty())
            throw new InvalidMoveException("Cell is empty");
        Choice choice=(Choice) parameters.get(1);
        if(choice.getChoice()==INCREASE) model.increase(player, cell);
        else model.decrease(player, cell);
        model.toolCardUsed(player, this);
    }

    /**
     * Informs the player which the next parameters are.
     * @return a Response which represents the next parameter.
     */
    @Override
    public Response next() {   //scelta
        if(expectedParameters.peek().equals(CHOICE))
            return Response.PINZA_SGROSSATRICE_CHOICE;
        return super.next();
    }
}
