package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.player.Player;
import server.state.utilities.GameRules;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TenagliaARotelle extends ToolCard {

    public TenagliaARotelle(Model model) {
        super(model);
    }

    @Override
    public void start(Player player) throws InvalidMoveException {
        if(player.isFirstMoveDone()) throw new InvalidMoveException("Only during your first turn");
        parameters=new ArrayList<>(3);
        expectedParameters=new ArrayDeque<>(3);
        expectedParameters.add("DraftPoolCell");
        expectedParameters.add("WindowFrame");
        expectedParameters.add("WindowFrameCell");
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