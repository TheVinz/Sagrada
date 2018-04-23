package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class MediumShadesTest {   //copertoo
    private WindowFrame windowFrame;
    private MediumShades mediumShades;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.SHADOW_THIEF;
        mediumShades = new MediumShades();
    }
    @Test
    public void shouldCalculatePoints(){
        try {
            windowFrame.getCell(3,2).put(new Dice(Color.BLUE, 3));
            windowFrame.getCell(1,3).put(new Dice(Color.BLUE, 4));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertEquals(2,mediumShades.calculatePoints(windowFrame));
    }


}