package server.state.objectivecards.privateobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard.*;

import static org.junit.Assert.*;

public class PrivateObjectiveCardTest {
    private WindowFrame windowFrame;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.SUN_CATCHER;
    }

    @Test
    public void shouldCalculatePoints(){
           try {
              windowFrame.getCell(3,2).put(new Dice(Color.BLUE, 4));
         } catch (InvalidMoveException e) {
            e.printStackTrace();
            }
        assertEquals(4, BLUE_SHAPES.calculatePoints(windowFrame));
    }

}