package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.player.Player;
import server.model.state.utilities.Color;

import java.util.*;

import static server.model.state.ModelObject.ModelType.*;

public class TamponeDiamantato extends ToolCard {

    public TamponeDiamantato(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 10;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

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