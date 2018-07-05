package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.player.Player;
import server.model.state.utilities.Color;

import java.util.*;

import static server.model.state.ModelObject.ModelType.*;

/**
 *  The <tt>TamponeDiamatato</tt> class represents the "Tampone Diamantato" {@link ToolCard}. The methods
 *  in this class handles the ToolCard's effect: after drafting, flip the {@link server.model.state.dice.Dice} to its opposite side
 */
public class TamponeDiamantato extends ToolCard {

    /**
     * Initializes the ToolCard TamponeDiamantato.
     * @param model the model of this game.
     */
    public TamponeDiamantato(Model model) {
        super(model);
    }

    /**
     * Gets the number of the ToolCard.
     * @return 10 which represents the TamponeDiamantato ToolCard.
     */
    @Override
    public int getNumber() {
        return 10;
    }

    /**
     * Gets the {@link Color} of the ToolCard.
     * @return the Color GREEN which represents the ToolCard's color.
     */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    /**
     * Initialize an array with the ToolCard's expected parameters.
     * @param player the {@link Player} whose using the ToolCard.
     * @throws InvalidMoveException if the DraftPool is empty.
     */
    @Override
    public void start(Player player) throws InvalidMoveException {
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        expectedParameters = new ArrayDeque<>(1);
        parameters = new ArrayList<>(1);
        expectedParameters.add(DRAFT_POOL_CELL);
        this.player=player;
    }

    @Override
    public void doAbility() throws InvalidMoveException{
        DraftPoolCell cell= (DraftPoolCell) parameters.get(0);
        if(cell.isEmpty())
            throw new InvalidMoveException("PoolCell is empty");
        model.flipDice(player, cell);
        model.toolCardUsed(player, this);
    }
}