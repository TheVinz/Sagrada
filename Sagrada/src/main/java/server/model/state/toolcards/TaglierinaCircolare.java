package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.roundtrack.RoundTrackCell;
import server.model.state.player.Player;

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
    public void start(Player player) throws InvalidMoveException{
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
        RoundTrackCell trackCell= (RoundTrackCell) parameters.get(1);
        model.exchange(player, poolCell, trackCell);
        model.toolCardUsed(player, this);
    }
}
