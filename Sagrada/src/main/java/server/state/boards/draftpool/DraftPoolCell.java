package server.state.boards.draftpool;

import server.state.ModelObject;
import server.state.dice.Dice;
import server.state.boards.Cell;
import common.exceptions.InvalidMoveException;

public class DraftPoolCell extends Cell implements ModelObject {

    private int index;

    public DraftPoolCell(int index){
        this.index=index;
    }

    public int getIndex(){
        return this.index;
    }
    @Override
    public int getType() {
        return DRAFT_POOL_CELL;
    }
}