package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.utilities.GameRules;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

public class TenagliaARotelle extends ToolCard {

    public TenagliaARotelle(Model model) {
        super(model);
    }

    @Override
    public int getNumber() {
        return 8;
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        if(player.isFirstMoveDone()) throw new InvalidMoveException("Only during your first turn");
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
       // if(player.getWindowFrame()!=frame) throw new InvalidMoveException("On your window frame");
        if(!GameRules.validAllDiceRestriction(frame, poolCell.getDice(), cell) ||
                !GameRules.validAllCellRestriction(poolCell.getDice(), cell))
            throw new InvalidMoveException("Move does not respect restrictions");
        else {
            model.move(player, poolCell, cell);
            model.toolCardUsed(player, this);
        }
    }
}
