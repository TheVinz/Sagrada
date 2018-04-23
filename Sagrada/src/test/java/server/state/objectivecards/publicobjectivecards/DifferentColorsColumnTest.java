package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentColorsColumnTest {
    private WindowFrame windowFrame;
    private DifferentColorsColumn differentColorsColumn;

    @Before
    public void initClass(){
        windowFrame = WindowFrame.AURORA_SAGRADIS;
        differentColorsColumn= new DifferentColorsColumn();

    }

    @Test
    public void shouldCalculatePoints(){

                assertEquals(0, differentColorsColumn.calculatePoints(windowFrame));
    }


}