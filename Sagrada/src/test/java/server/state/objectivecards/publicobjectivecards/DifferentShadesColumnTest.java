package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentShadesColumnTest {   //nada

    private WindowFrame windowFrame;
    private DifferentShadesColumn differentShadesColumn;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.AURORA_SAGRADIS;
       differentShadesColumn = new DifferentShadesColumn();
    }

    @Test
    public void shouldCalculatePoints(){

        assertEquals(0,differentShadesColumn.calculatePoints(windowFrame));
    }

}