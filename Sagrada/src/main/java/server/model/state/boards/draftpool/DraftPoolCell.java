package server.model.state.boards.draftpool;

import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.Cell;

import static server.model.state.ModelObject.ModelType.DRAFT_POOL_CELL;

public class DraftPoolCell extends Cell implements ModelObject {

    private int index;

    public DraftPoolCell(int index){
        this.index=index;
    }

    public int getIndex(){
        return this.index;
    }
    @Override
    public ModelType getType() {
        return DRAFT_POOL_CELL;
    }
}