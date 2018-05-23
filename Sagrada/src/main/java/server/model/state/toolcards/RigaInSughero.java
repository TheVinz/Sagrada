package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.utilities.GameRules;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

public class RigaInSughero extends ToolCard {
    public RigaInSughero(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 9;
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
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
        WindowFrame frame= (WindowFrame) parameters.get(1);
        WindowFrameCell cell= (WindowFrameCell) parameters.get(2);
        if(GameRules.validAdjacentDices(frame, cell))
            throw new InvalidMoveException("Cell must have no adjacent dices");
        else if(!GameRules.validAllCellRestriction(poolCell.getDice(), cell))
            throw new InvalidMoveException("Placement must respect cell restrictions");
        else{
            model.move(player, poolCell, cell);
            model.toolCardUsed(player, this);
        }
    }

    @Override
    public Response next() {  //trascinamento da draftpool
        if(expectedParameters.peek().equals(DRAFT_POOL_CELL))
            return Response.DRAFT_POOL_MOVE;
        else
            return null;
    }
}
