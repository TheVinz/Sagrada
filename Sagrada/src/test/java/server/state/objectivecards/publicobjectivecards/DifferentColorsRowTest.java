package server.state.objectivecards.publicobjectivecards;

import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;

import static org.junit.Assert.*;

public class DifferentColorsRowTest {   //nada
    private WindowFrame windowFrame;
    private DifferentColorsRow differentColorsRow;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.AURORA_SAGRADIS;
        differentColorsRow = new DifferentColorsRow();
    }
    @Test
    public void shouldCalculatePoints(){


        assertEquals(0,differentColorsRow.calculatePoints(windowFrame));
    }


}