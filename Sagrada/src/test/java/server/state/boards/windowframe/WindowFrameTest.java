package server.state.boards.windowframe;

import static org.junit.Assert.*;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

public class WindowFrameTest {
    private WindowFrame windowFrame;
    private Dice dice;
    @Before
            public void init()
    {
        windowFrame = new WindowFrame(WindowFrameList.AURORA_SAGRADIS);
        dice = new Dice(Color.RED,1);
    }

    @Test
    public void shouldGetCell(){
        assertNotEquals(null, windowFrame.getCell(0,0));
    }
    @Test
    public void shouldClean() throws InvalidMoveException {
        windowFrame.getCell(0,1).put(dice);
        assertNotEquals(dice,windowFrame.getCell(0,1));
    }
    @Test
    public void shouldGetFavorToken(){
        assertEquals(4,windowFrame.getFavorToken());
    }
    @Test
    public void shouldGetRep(){
        assertEquals("r0b0y4p3g20105000600",windowFrame.getRep());
    }
}