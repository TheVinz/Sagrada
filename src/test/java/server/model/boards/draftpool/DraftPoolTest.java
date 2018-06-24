package server.model.boards.draftpool;


import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.bag.Bag;
import server.model.state.boards.draftpool.DraftPool;

import static org.junit.Assert.*;

public class DraftPoolTest {

    private DraftPool draftPool;
    @Before
    public void initClass()
    {
        draftPool = new DraftPool();
    }
    @Test
    public void shouldIncreaseSize ()
    {
        for(int i=0; i<5; i++)
        {
            assertEquals(Math.min(9, 1+i*2), draftPool.getSize());
            if(i == 4)
                break;
            try{
                draftPool.increaseSize();
            }
            catch (Exception e){
                fail("Should not be exception");
            }
        }
        try{
            draftPool.increaseSize();
            fail("Should be exception");
        }
        catch (Exception e){
            assertEquals("The game is full", e.getMessage());
        }
    }
    @Test
    public void shouldGetDraftPool()
    {
        assertNotEquals(null, draftPool.getDraftPool());
    }

    @Test
    public void shouldDrow() throws InvalidMoveException {
        draftPool.draw(new Bag());
        assertEquals(draftPool.getDraftPool().size(), draftPool.getSize());
    }

    @Test
    public void shouldGetCell()
    {
        assertNotEquals(null, draftPool.getCell(0));
    }

    @Test
    public void shouldIncreaseSizeByOne(){
        draftPool.increaseSizeByOne();
        assertEquals(2,draftPool.getSize());
    }
}