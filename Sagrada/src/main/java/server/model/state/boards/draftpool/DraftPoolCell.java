package server.model.state.boards.draftpool;

import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.Cell;

import static server.model.state.ModelObject.ModelType.DRAFT_POOL_CELL;

/**
 * The DraftPoolCell class extends the {@link server.model.state.boards.Cell} class.
 * Represents the cell in the {@link server.model.state.boards.draftpool.DraftPool}.
 */
public class DraftPoolCell extends Cell implements ModelObject {

    private int index;

    /**
     * Initialize a DraftPoolCell in the DraftPool at given index.
     * @param index indicates where the DraftPoolCell is in the DraftPool.
     */
    public DraftPoolCell(int index){
        this.index=index;
    }

    /**
     * Gets the position of the DraftPoolCell in the DraftPool.
     * @return an int that represents the index of the DraftPoolCell in the DraftPool.
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * Gets the type of this Object.
     * @return DRAFT_POOL_CELL type.
     */
    @Override
    public ModelType getType() {
        return DRAFT_POOL_CELL;
    }
}