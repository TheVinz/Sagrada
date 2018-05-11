package server.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameList;
import server.state.player.Player;

import static org.junit.Assert.*;

public class ControllerTest {

    private Model model;
    private Player player;
    private Controller controller;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        player= Mockito.spy(new Player("Scotti", 1, model));
        controller= new Controller(model, player);
        WindowFrame frame = new WindowFrame(WindowFrameList.SUN_CATCHER);
        Mockito.when(player.getWindowFrame()).thenReturn(frame);
    }

    @Test
    public void selectObject() {

    }
}