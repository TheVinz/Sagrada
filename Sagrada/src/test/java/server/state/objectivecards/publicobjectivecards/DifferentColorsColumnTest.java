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
        windowFrame = WindowFrame.KALEIDOSCOPIC_DREAM;
        differentColorsColumn= new DifferentColorsColumn();

    }

    @Test
    public void shouldCalculatePoints(){
                assertEquals(0, differentColorsColumn.calculatePoints(windowFrame));
        try {
            windowFrame.getCell(0,0).put(new Dice(Color.YELLOW));
            windowFrame.getCell(1,0).put(new Dice(Color.RED));
            windowFrame.getCell(2,0).put(new Dice(Color.BLUE));
            windowFrame.getCell(3,0).put(new Dice(Color.GREEN));
            windowFrame.getCell(0,1).put(new Dice(Color.PURPLE));
            windowFrame.getCell(3,1).put(new Dice(Color.YELLOW));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertEquals(5, differentColorsColumn.calculatePoints(windowFrame));
    }


}