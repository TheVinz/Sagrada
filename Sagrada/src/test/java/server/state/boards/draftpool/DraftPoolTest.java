package server.state.boards.draftpool;


import org.junit.Before;
import org.junit.Test;
import server.state.bag.Bag;

import static org.junit.Assert.*;

public class DraftPoolTest {

    private DraftPool draftPool;
    @Before
    public void initClass()
    {
        draftPool = new DraftPool();
    }
    @Test
    public void shouldIncreaseSize()
    {
        for(int i=0; i<6; i++)
        {
            assertEquals(Math.min(9, 1+i*2), draftPool.getSize());
            draftPool.increaseSize();

        }
    }
    @Test
    public void shouldGetDraftPool()
    {
        assertNotEquals(null, draftPool.getDraftPool());
    }

    @Test
    public void shouldDrow()
    {
        draftPool.drow(new Bag());
        assertEquals(draftPool.getDraftPool().length, draftPool.getSize());
    }

    @Test
    public void shouldGetCell()
    {
        assertNotEquals(null, draftPool.getCell(0));
    }

}