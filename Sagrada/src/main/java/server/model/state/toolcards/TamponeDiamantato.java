package server.model.state.toolcards;

import server.model.Model;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.player.Player;

import java.util.*;

public class TamponeDiamantato extends ToolCard {

    public TamponeDiamantato(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 10;
    }

    @Override
    public void start(Player player) {
        expectedParameters = new ArrayDeque<>(1);
        parameters = new ArrayList<>(1);
        expectedParameters.add(DRAFT_POOL_CELL);
        this.player=player;
    }

    @Override
    public void doAbility() {
        DraftPoolCell cell= (DraftPoolCell) parameters.get(0);
        model.flipDice(player, cell);
        model.toolCardUsed(player, this);
    }
}