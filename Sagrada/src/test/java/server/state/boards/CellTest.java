package server.state.boards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class CellTest {

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
            catch (InvalidMoveException e)
            {
                assertEquals(e.getMessage(),  "Empty cell");
            }
            finally {
                try {
                    draftPoolCell.put(new Dice(Color.RED));
                }
                catch (InvalidMoveException e){
                }
                try{
                    draftPoolCell.removeDice();
                    assertEquals(true, draftPoolCell.isEmpty());
                }catch (InvalidMoveException e){
                    fail("Should not be exception");
                }


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