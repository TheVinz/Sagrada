package server.model.state.boards.draftpool;

import server.model.state.ModelObject;
import server.model.state.boards.Cell;

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