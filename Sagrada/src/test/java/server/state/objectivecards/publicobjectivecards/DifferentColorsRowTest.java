package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentColorsRowTest {   //nada
    private WindowFrame windowFrame;
    private DifferentColorsRow differentColorsRow;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.VIRTUS;
        differentColorsRow = new DifferentColorsRow();
    }
    @Test
    public void shouldCalculatePoints(){
        assertEquals(0,differentColorsRow.calculatePoints(windowFrame));
        try {
            windowFrame.getCell(0,0).put(new Dice(Color.GREEN));
            windowFrame.getCell(0,1).put(new Dice(Color.YELLOW));
            windowFrame.getCell(0,2).put(new Dice(Color.BLUE));
            windowFrame.getCell(0,3).put(new Dice(Color.RED));
            windowFrame.getCell(0,4).put(new Dice(Color.PURPLE));
            windowFrame.getCell(1,0).put(new Dice(Color.GREEN));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertEquals(6, differentColorsRow.calculatePoints(windowFrame));
    }


}