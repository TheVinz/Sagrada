package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class DarkShadesTest {    //copertoo
    private WindowFrame windowFrame;
    private DarkShades darkShades;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.BATLLO;
        darkShades = new DarkShades();
    }
    @Test
    public void shouldCalculatePoints(){
        try {
            windowFrame.getCell(3,2).put(new Dice(Color.BLUE, 5));
            windowFrame.getCell(1,3).put(new Dice(Color.BLUE, 6));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertEquals(2,darkShades.calculatePoints(windowFrame));
    }


}