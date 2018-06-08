package server.state.boards.roundtrack;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.dice.Dice;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class RoundTrackTest {
    private DraftPool draftPool;
    private RoundTrack roundTrack;

    @Before
    public void setUp() throws Exception {
        draftPool = mock(DraftPool.class);
        roundTrack = new RoundTrack();
    }
    @Test
    public void shouldEndRound() throws InvalidMoveException, Exception {
        when(draftPool.getSize()).thenReturn(0);
        assertEquals(0, roundTrack.getRoundSet(1).size());
        roundTrack.endRound(draftPool);
        assertEquals(0, roundTrack.getRoundSet(1).size());
        Dice dice1 = mock(Dice.class);
        Dice dice2 = mock(Dice.class);
        DraftPoolCell draftPoolCell1 = mock(DraftPoolCell.class);
        DraftPoolCell draftPoolCell2 = mock(DraftPoolCell.class);
        DraftPoolCell draftPoolCell3 = mock(DraftPoolCell.class);
        when(draftPool.getCell(0)).thenReturn(draftPoolCell1);
        when(draftPool.getCell(1)).thenReturn(draftPoolCell2);
        when(draftPool.getCell(2)).thenReturn(draftPoolCell3);
        when(draftPool.getSize()).thenReturn(3);
        when(draftPoolCell1.getDice()).thenReturn(dice1);
        when(draftPoolCell2.getDice()).thenReturn(null);
        when(draftPoolCell3.getDice()).thenReturn(dice2);
        when(draftPoolCell1.removeDice()).thenReturn(dice1);
        when(draftPoolCell2.removeDice()).thenReturn(null);
        when(draftPoolCell3.removeDice()).thenReturn(dice2);
        when(dice1.getValue()).thenReturn(3);
        when(dice2.getValue()).thenReturn(4);
        roundTrack.endRound(draftPool);
        assertEquals(2, roundTrack.getRoundSet(2).size());
        assertEquals(dice2, roundTrack.getRoundSet(2).get(1).getDice());
        assertEquals(dice1, roundTrack.getRoundSet(2).get(0).getDice());
    }
    @Test
    public void shouldGetRound() throws InvalidMoveException{
        for(int i=1; i<=10; i++)
        {
            assertEquals(i, roundTrack.getRound());
            try{
                roundTrack.endRound(draftPool);
            }
            catch (Exception e){
                fail("Should not be exception");
            }
        }
        assertEquals(11, roundTrack.getRound());
        try{
            roundTrack.endRound(draftPool);
            fail("Should not be exception");
        }
        catch (Exception e)
        {
            assertEquals("Game is finished", e.getMessage());
        }
    }
    @Test
    public void shouldCalculatePoints() throws Exception {
        when(draftPool.getSize()).thenReturn(0);
        assertEquals(0, roundTrack.getRoundSet(1).size());
        roundTrack.endRound(draftPool);
        assertEquals(0, roundTrack.getRoundSet(1).size());
        Dice dice1 = mock(Dice.class);
        Dice dice2 = mock(Dice.class);
        DraftPoolCell draftPoolCell1 = mock(DraftPoolCell.class);
        DraftPoolCell draftPoolCell2 = mock(DraftPoolCell.class);
        DraftPoolCell draftPoolCell3 = mock(DraftPoolCell.class);
        when(draftPool.getCell(0)).thenReturn(draftPoolCell1);
        when(draftPool.getCell(1)).thenReturn(draftPoolCell2);
        when(draftPool.getCell(2)).thenReturn(draftPoolCell3);
        when(draftPool.getSize()).thenReturn(3);
        when(draftPoolCell1.getDice()).thenReturn(dice1);
        when(draftPoolCell2.getDice()).thenReturn(null);
        when(draftPoolCell3.getDice()).thenReturn(dice2);
        when(draftPoolCell1.removeDice()).thenReturn(dice1);
        when(draftPoolCell2.removeDice()).thenReturn(null);
        when(draftPoolCell3.removeDice()).thenReturn(dice2);
        when(dice1.getValue()).thenReturn(3);
        when(dice2.getValue()).thenReturn(4);
        roundTrack.endRound(draftPool);
        assertEquals(7,roundTrack.calculatePoints());
    }
    @Test
    public void shouldIsEmpty(){
       assertEquals(true,roundTrack.isEmpty());
    }
}