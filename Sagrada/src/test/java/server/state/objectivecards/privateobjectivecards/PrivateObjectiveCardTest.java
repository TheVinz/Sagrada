package server.state.objectivecards.privateobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

import static server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard.*;

import static org.junit.Assert.*;

public class PrivateObjectiveCardTest {
    private WindowFrame windowFrame;
    @Before
    public void initClass(){
        windowFrame = new WindowFrame(WindowFrameList.SUN_CATCHER);
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
    @Test
    public void shouldGetColor(){
        assertEquals(Color.PURPLE,PURPLE_SHAPES.getColor());
    }

}