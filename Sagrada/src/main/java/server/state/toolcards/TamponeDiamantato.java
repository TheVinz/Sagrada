package server.state.toolcards;

import server.Model;
import server.state.boards.draftpool.DraftPool;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.dice.Dice;
import common.exceptions.InvalidMoveException;
import server.state.player.Player;

import java.util.*;

public class TamponeDiamantato extends ToolCard {

    public TamponeDiamantato(Model model) {
        super(model);
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        expectedParameters = new ArrayDeque<>(1);
        parameters = new ArrayList<>(1);
        expectedParameters.add(DRAFT_POOL_CELL);
        this.player=player;
    }

    @Override
    public void doAbility() throws InvalidMoveException {
        DraftPoolCell cell= (DraftPoolCell) parameters.get(0);
        model.flipDice(player, cell);
        model.toolCardUsed(player, this);
    }
}