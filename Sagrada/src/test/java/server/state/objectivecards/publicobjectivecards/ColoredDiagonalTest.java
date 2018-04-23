package server.state.objectivecards.publicobjectivecards;

import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;

import static org.junit.Assert.*;

public class ColoredDiagonalTest {     //nullpointerexception
    private WindowFrame windowFrame;
    private ColoredDiagonal coloredDiagonal;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.AURORA_SAGRADIS;
        coloredDiagonal = new ColoredDiagonal();
    }
    @Test
    public void shouldCalculatePoints(){
        assertEquals(0,coloredDiagonal.calculatePoints(windowFrame));
    }


}