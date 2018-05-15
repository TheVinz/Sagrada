package server.controller;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.model.Model;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.PinzaSgrossatrice;
import server.model.state.utilities.Choice;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class ControllerTest {

    private Model model;
    private Player player;
    private Controller controller;

    @Before
    public void setUp() throws Exception {
        model = Mockito.spy(new Model());
        player= Mockito.spy(new Player("Scotti", 1));
        controller= new Controller(model, player);
        WindowFrame frame = new WindowFrame(WindowFrameList.SUN_CATCHER);
        Mockito.when(player.getWindowFrame()).thenReturn(frame);
        model.getState().getDraftPool().increaseSize();
        model.getState().getDraftPool().getCell(0).put(new Dice(Color.BLUE, 6));
        model.getState().getDraftPool().getCell(1).put(new Dice(Color.YELLOW, 3));
        player.setActive();
    }

    @Test
    public void selectObject() throws InvalidMoveException {

        controller.selectObject(new PinzaSgrossatrice(model));

        assertEquals(UsingToolCard.class, controller.getCurrentState().getClass());

        controller.selectObject(model.getState().getDraftPool().getCell(0));
        controller.selectObject(new Choice(PinzaSgrossatrice.DECREASE));

        assertEquals(5, model.getState().getDraftPool().getCell(0).getDice().getValue());

        controller.selectObject(model.getState().getDraftPool().getCell(0));

        assertEquals(MovingDice.class, controller.getCurrentState().getClass());

        controller.selectObject(player.getWindowFrame().getCell(0,0));

        assertEquals(WaitingState.class, controller.getCurrentState().getClass());
        assertTrue(model.getState().getDraftPool().getCell(0).isEmpty());
        assertFalse(player.getWindowFrame().getCell(0,0).isEmpty());

        controller.selectObject(model.getState().getDraftPool().getCell(1));

        assertEquals(WaitingState.class, controller.getCurrentState().getClass());

        controller.selectObject(player.getWindowFrame().getCell(1,1));

        assertEquals(WaitingState.class, controller.getCurrentState().getClass());
        assertFalse(model.getState().getDraftPool().getCell(1).isEmpty());
        assertTrue(player.getWindowFrame().getCell(1,1).isEmpty());

    }
}