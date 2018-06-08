package server.model.boards.draftpool;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class DraftPoolCellTest {
    private DraftPoolCell draftPoolCell;
    private DraftPoolCell draftPoolCell1;
    @Before
    public void init() throws InvalidMoveException {
        draftPoolCell = new DraftPoolCell(0);
        draftPoolCell1 = new DraftPoolCell(1);
        draftPoolCell1.put(new Dice(Color.BLUE,4));
    }
    @Test
    public void shouldGetIndex(){
        assertEquals(0,draftPoolCell.getIndex());
    }
    @Test
    public void shouldGetType(){
        assertEquals(ModelType.DRAFT_POOL_CELL,draftPoolCell1.getType());
    }

}