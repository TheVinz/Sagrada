package server;

import common.ChangementVisitor;
import common.viewchangement.CellUpdate;
import common.viewchangement.Changement;
import server.state.boards.Cell;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.dice.Dice;
import server.state.player.Player;


import static server.state.ModelObject.*;

public class ViewNotifier {
    private ChangementVisitor changementVisitor;

    public ViewNotifier(ChangementVisitor  changementVisitor){
        this.changementVisitor = changementVisitor;
    }

    public void notifyCellChangement(Player player, Cell target){

        Changement changement;
        switch (target.getType()) {
            case WINDOW_FRAME_CELL:
                WindowFrameCell windowFrameCell = (WindowFrameCell) target;
                changement = new CellUpdate(player.toString(), target.getType(), windowFrameCell.getRow(), windowFrameCell.getColumnn(), target.getDice().getValue(), target.getDice().getColor());
                changement.change(changementVisitor);
                break;
            case DRAFT_POOL_CELL:

                break;
            case ROUND_TRACK_CELL:

                break;
        }
    }


    public void putDice(Player player, Dice dice, Cell first){

    }
}
