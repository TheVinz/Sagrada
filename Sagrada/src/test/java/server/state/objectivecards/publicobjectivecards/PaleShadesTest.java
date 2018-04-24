package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class PaleShadesTest {    //coperto
    private WindowFrame windowFrame;
    private PaleShades paleShades;

    @Before
    public void initClass() {
        windowFrame = WindowFrame.LUZ_CELESTIAL;
        paleShades = new PaleShades();
    }

    @Test
    public void shouldCalculatePoints() {
        assertEquals(0, paleShades.calculatePoints(windowFrame));   //vetrata vuota
        try {
            windowFrame.getCell(3, 2).put(new Dice(Color.BLUE, 1));
            windowFrame.getCell(1, 3).put(new Dice(Color.BLUE, 2));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertEquals(2, paleShades.calculatePoints(windowFrame));  //vetrata con due dadi, un uno e un due


        try {
            windowFrame.getCell(3, 1).put(new Dice(Color.RED, 3));
            windowFrame.getCell(2, 3).put(new Dice(Color.PURPLE, 1));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertEquals(2,paleShades.calculatePoints(windowFrame));  //vetrata con 4 dadi, uno da scartare e tre della carta
    }
}