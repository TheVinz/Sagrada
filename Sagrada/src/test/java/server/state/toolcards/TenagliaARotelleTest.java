package server.state.toolcards;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.Model;
import server.state.player.Player;

import static org.junit.Assert.*;

public class TenagliaARotelleTest {
    private Model model;
    private ToolCard test;
    private Player player;

    @Before
    public void setUp() throws Exception {
        model = Mockito.mock(Model.class);
        player = Mockito.mock(Player.class);
        test = new TenagliaARotelle(model);


    }

    @Test
    public void doAbility() {}
}