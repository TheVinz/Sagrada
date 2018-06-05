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

public class TaglierinaCircolare extends ToolCard{
    public TaglierinaCircolare(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 5;
    }

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
            throw new InvalidMoveException("TrackCell is empty");
        model.exchange(player, poolCell, trackCell);
        model.toolCardUsed(player, this);
    }
}
