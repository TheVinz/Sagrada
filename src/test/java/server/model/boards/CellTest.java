package server.model.boards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class CellTest {

        private DraftPoolCell draftPoolCell;
        @Before
        public void initClass()
        {
            draftPoolCell = new DraftPoolCell(0);
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
                assertEquals("Empty cell",e.getMessage());
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
                draftPoolCell.move(new DraftPoolCell(0));
                fail("Should be exception");
            }
            catch (InvalidMoveException e)
            {
                assertEquals("Empty cell", e.getMessage());
            }
            finally {
                try {
                    draftPoolCell.put(new Dice(Color.RED));
                }
                catch (InvalidMoveException e){
                }
                try {
                    draftPoolCell.move(new DraftPoolCell(0));
                } catch (InvalidMoveException e) {
                }
                assertEquals(true, draftPoolCell.isEmpty());
            }
        }

    }