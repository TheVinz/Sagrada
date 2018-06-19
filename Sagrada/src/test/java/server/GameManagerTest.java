package server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.model.Model;
import server.viewproxy.ViewProxy;

import static org.junit.Assert.*;

public class GameManagerTest {

    private GameManager test;

    @Before
    public void setUp() {
        test = new GameManager();
    }

    @Test
    public void addPlayer() {
        ViewProxy connection1 = Mockito.mock(ViewProxy.class);
        Model model = test.getCurrentModel();
        test.addPlayer("test1", connection1, false);

        assertEquals(model, test.getCurrentModel());
        assertTrue(test.getGamesMap().containsKey("test1"));

        ViewProxy connection2 = Mockito.mock(ViewProxy.class);
    }

    @Test
    public void startGame() {
    }

    @Test
    public void endGame() {
    }

    @Test
    public void removePlayer() {
    }

}