package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentShadesColumnTest {   //nada

    private WindowFrame windowFrame;
    private DifferentShadesColumn differentShadesColumn;
    @Before
    public void initClass(){
        windowFrame = new WindowFrame(WindowFrameList.FULGOR_DEL_CIELO);
       differentShadesColumn = new DifferentShadesColumn();
    }

    @Test
    public void shouldCalculatePoints() throws InvalidMoveException {
        assertEquals(0,differentShadesColumn.calculatePoints(windowFrame));
        windowFrame.getCell(0,0).put(new Dice(Color.GREEN, 1));
        windowFrame.getCell(1,0).put(new Dice(Color.GREEN, 2));
        windowFrame.getCell(2,0).put(new Dice(Color.GREEN, 3));
        windowFrame.getCell(3,0).put(new Dice(Color.GREEN, 4));
        windowFrame.getCell(1,1).put(new Dice(Color.GREEN, 5));
        windowFrame.getCell(1,2).put(new Dice(Color.GREEN, 6));
        assertEquals(4, differentShadesColumn.calculatePoints(windowFrame));
    }
}