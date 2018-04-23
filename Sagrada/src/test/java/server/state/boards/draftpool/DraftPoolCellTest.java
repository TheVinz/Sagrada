package server.state.boards.draftpool;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class DraftPoolCellTest {
    private DraftPoolCell draftPoolCell;
    @Before
    public void initClass()
    {
        draftPoolCell = new DraftPoolCell();
    }

    @Test
    public void shouldPut() throws InvalidMoveException
    {
        draftPoolCell.put(new Dice(Color.RED));
        assertEquals(false, draftPoolCell.isEmpty());
        try{
            draftPoolCell.put(new Dice(Color.RED));
            fail("Should be exception!");
        }
        catch (InvalidMoveException e)
        {
            assertEquals("Already filled cell", e.getMessage());
        }

    }

    @Test
    public void shouldRemove()
    {
        try{
            draftPoolCell.removeDice();
            fail("Should be exception!");
        }
        /*catch (InvalidMoveException e)
        {
            assertEquals(e.getMessage(),  "No dice present");
        }*/
        finally {
            try {
                draftPoolCell.put(new Dice(Color.RED));
            }
            catch (InvalidMoveException e){
            }

            draftPoolCell.removeDice();
            assertEquals(true, draftPoolCell.isEmpty());
        }
    }
    @Test
    public void shouldMove()
    {
        try{
            draftPoolCell.move(new DraftPoolCell());
            fail("Should be exception");
        }
        catch (InvalidMoveException e)
        {
            assertEquals(e.getMessage(), "Empty cell");
        }
        finally {
            try {
                draftPoolCell.put(new Dice(Color.RED));
            }
            catch (InvalidMoveException e){
            }
            try {
                draftPoolCell.move(new DraftPoolCell());
            } catch (InvalidMoveException e) {
            }
            assertEquals(true, draftPoolCell.isEmpty());
        }
    }

}