package server.state.toolcards;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.Model;
import server.state.boards.draftpool.DraftPool;
import server.state.player.Player;

import static org.junit.Assert.*;

public class TamponeDiamantatoTest {


    private Model model;
    private ToolCard test;
    private Player player;
    private DraftPool draftPool;

    @Before
    public void setUp() throws Exception {
        model= Mockito.mock(Model.class);
        player=Mockito.mock(Player.class);
        draftPool = new DraftPool();
    }


    @Test
    public void doAbility() {
    }
}