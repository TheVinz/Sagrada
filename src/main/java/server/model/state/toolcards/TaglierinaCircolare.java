package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.roundtrack.RoundTrackCell;
import server.model.state.player.Player;
import server.model.state.utilities.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

/**
 * The <tt>TaglierinaCircolare</tt> class represents the "Taglierina circolare" {@link ToolCard}. The methods
 * in this class handles the ToolCard's effect: after drafting, swap the drafted {@link server.model.state.dice.Dice}
 * with a dice from the {@link server.model.state.boards.roundtrack.RoundTrack}.
 */
public class TaglierinaCircolare extends ToolCard{

    /**
     * Initializes the ToolCard TaglierinaCircolare.
     * @param model the model of this game.
     */
    public TaglierinaCircolare(Model model) {
        super(model);
    }

    /**
     * Gets the number of the ToolCard.
     * @return 5 which represents the TaglierinaCircolare ToolCard.
     */
    @Override
    public int getNumber() {
        return 5;
    }

    /**
     * Gets the {@link Color} of the ToolCard.
     * @return the Color GREEN which represents the ToolCard's color.
     */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public void start(Player player) throws InvalidMoveException{
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        if(model.getState().getRoundTrack().isEmpty())
            throw new InvalidMoveException("Empty round track");
        expectedParameters=new ArrayDeque<>(2);
        parameters=new ArrayList<>(2);
        expectedParameters.add(DRAFT_POOL_CELL);
        expectedParameters.add(ROUND_TRACK_CELL);
        this.player=player;
    }

    @Override
    void doAbility() throws InvalidMoveException {
        DraftPoolCell poolCell= (DraftPoolCell) parameters.get(0);
        if(poolCell.isEmpty())
            throw new InvalidMoveException("PoolCell is empty");
        RoundTrackCell trackCell= (RoundTrackCell) parameters.get(1);
        if(trackCell.isEmpty())
            throw new InvalidMoveException("TrackCell is empty");   //non ci sarò mai una cella del roundtrack vuota
        model.exchange(player, poolCell, trackCell);
        model.toolCardUsed(player, this);
    }
}
