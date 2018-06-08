package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import server.model.Model;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.PennelloPerEglomise;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PennelloPerEglomiseTest {

    private ToolCard test;
    private Player player;

    @Before
    public void setUp() throws Exception {
        Model model=new Model();
        test= new PennelloPerEglomise(model);
        player = Mockito.mock(Player.class);
        WindowFrame frame = new WindowFrame(WindowFrameList.BATLLO);
        when(player.getWindowFrame()).thenReturn(frame);
        player.getWindowFrame().getCell(1,3).put(new Dice(Color.BLUE, 4));
        player.getWindowFrame().getCell(2,4).put(new Dice(Color.YELLOW, 6));
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        test.start(player);

        WindowFrame frame=new WindowFrame(WindowFrameList.AURORA_SAGRADIS);

        try {
            test.setParameter(frame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(frame.getCell(2,2));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                test.setParameter(player.getWindowFrame().getCell(2, 3));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch(InvalidMoveException e){
            assertEquals("Wrong parameter", e.getMessage());
        }
        assertFalse(player.getWindowFrame().getCell(1,3).isEmpty());
        assertFalse(player.getWindowFrame().getCell(2,4).isEmpty());



        test.start(player);

        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(1,3));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                test.setParameter(player.getWindowFrame().getCell(3, 4));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch(InvalidMoveException e){
            assertEquals("Cell and dice shapes must be equal", e.getMessage());
        }

        assertFalse(player.getWindowFrame().getCell(1,3).isEmpty());
        assertFalse(player.getWindowFrame().getCell(2,4).isEmpty());
        assertTrue(player.getWindowFrame().getCell(3,4).isEmpty());

        test.start(player);

        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(1,3));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                test.setParameter(player.getWindowFrame().getCell(0, 0));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        } catch(InvalidMoveException e){
            assertEquals("Invalid adjacent dices", e.getMessage());
        }

        test.start(player);

        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(1,3));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(2,3));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertEquals(Color.BLUE, player.getWindowFrame().getCell(2,3).getDice().getColor());
    }
}