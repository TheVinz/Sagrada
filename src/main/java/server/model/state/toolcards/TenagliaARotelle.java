package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.utilities.Color;
import server.model.state.utilities.GameRules;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

/**
 *  The <tt>TenagliaARotelle</tt> class represents the "Tenaglia a rotelle" {@link ToolCard}. The methods
 *  in this class handles the ToolCard's effect: after your first turn immediately draft a {@link server.model.state.dice.Dice}.
 */
public class TenagliaARotelle extends ToolCard {

    /**
     * Initializes the ToolCard TenagliaARotelle.
     * @param model the model of this game.
     */
    public TenagliaARotelle(Model model) {
        super(model);
    }

    /**
     * Gets the number of the ToolCard.
     * @return 8 which represents the TenagliaARotelle ToolCard.
     */
    @Override
    public int getNumber() {
        return 8;
    }

    /**
     * Gets the {@link Color} of the ToolCard.
     * @return the Color RED which represents the ToolCard's color.
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }

    /**
     * Initialize an array with the ToolCard's expected parameters.
     * @param player the {@link Player} whose using the ToolCard.
     * @throws InvalidMoveException if the {@link server.model.state.boards.draftpool.DraftPool} is empty.
     */
    @Override
    public void start(Player player) throws InvalidMoveException {
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        if(player.isSecondTurn())
            throw new InvalidMoveException("Only during your first turn");
        if(!player.isDiceMoved())
            throw new InvalidMoveException("You have to place a dice first");
        parameters=new ArrayList<>(3);
        expectedParameters=new ArrayDeque<>(3);
        expectedParameters.add(DRAFT_POOL_CELL);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        this.player=player;
    }

    @Override
    void doAbility() throws InvalidMoveException {
        DraftPoolCell poolCell= (DraftPoolCell) parameters.get(0);
        if(poolCell.isEmpty())
            throw new InvalidMoveException("PoolCell is empty");
        WindowFrame frame= (WindowFrame) parameters.get(1);
        WindowFrameCell cell= (WindowFrameCell) parameters.get(2);
        if(!GameRules.validAllDiceRestriction(frame, poolCell.getDice(), cell) ||
                !GameRules.validAllCellRestriction(poolCell.getDice(), cell))
            throw new InvalidMoveException("Move does not respect restrictions");
        else {
            model.move(player, poolCell, cell);
            model.toolCardUsed(player, this);
            player.setJumpSecondTurn(true);
        }
    }

    /**
     * Informs the player which the next parameters are.
     * @return a Response which represents the next parameter.
     */
    @Override
    public Response next() {   //doppio trascinamento dalla draftpool
        if(expectedParameters.peek().equals(DRAFT_POOL_CELL))
            return Response.DRAFT_POOL_MOVE;
        else
            return null;
    }
}
