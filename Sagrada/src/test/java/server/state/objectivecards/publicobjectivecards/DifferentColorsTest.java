package server.state.objectivecards.publicobjectivecards;

import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;

import static org.junit.Assert.*;

public class DifferentColorsTest {  //andata con vetrata vuota
    private WindowFrame windowFrame;
    private DifferentColors differentColors;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.AURORA_SAGRADIS;
        differentColors = new DifferentColors();
    }
    @Test
    public void shouldCalculatePoints(){
        assertEquals(0,differentColors.calculatePoints(windowFrame));
    }


}