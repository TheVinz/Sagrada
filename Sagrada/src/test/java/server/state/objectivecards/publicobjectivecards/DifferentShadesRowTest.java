package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentShadesRowTest {   //Scritta male la carta missa
    private WindowFrame windowFrame;
    private DifferentShadesRow differentShadesRow;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.AURORA_SAGRADIS;
       differentShadesRow = new DifferentShadesRow();
    }
    @Test
    public void shouldCalculatePoints(){


        assertEquals(0,differentShadesRow.calculatePoints(windowFrame));
    }


}