package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.roundtrack.RoundTrackCell;
import server.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TaglierinaCircolare extends ToolCard{
    public TaglierinaCircolare(Model model) {
        super(model);
    }

    @Override
    public void start(Player player) {
        expectedParameters=new ArrayDeque<>(2);
        parameters=new ArrayList<>(2);
        expectedParameters.add("DraftPoolCell");
        expectedParameters.add("RoundTrackCell");
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
