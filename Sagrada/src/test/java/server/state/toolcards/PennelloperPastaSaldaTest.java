package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.player.Player;
import server.Model;
import server.state.utilities.Color;

import javax.tools.Tool;

import static org.junit.Assert.*;

public class PennelloperPastaSaldaTest {

    private Player player;
    private ToolCard test;
    private Model model;

    @Before
    public void setUp() throws Exception {
        model=Mockito.spy(new Model());
        test=new PennelloperPastaSalda(model);
        player=Mockito.mock(Player.class);
        WindowFrame frame=new WindowFrame(WindowFrameList.GRAVITAS);
        Mockito.when(player.getWindowFrame()).thenReturn(frame);
        model.getState().getDraftPool().getCell(0).put(new Dice(Color.BLUE,4));
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        test.start(player);
        test.setParameter(model.getState().getDraftPool().getCell(0));
        assertFalse(test.hasNext());

        player.getWindowFrame().getCell(0,0).put(new Dice(Color.YELLOW, 3));
        player.getWindowFrame().getCell(1,0).put(new Dice(Color.YELLOW, 3));

        test.start(player);
        test.setParameter(model.getState().getDraftPool().getCell(0));
        while(model.getState().getDraftPool().getCell(0).getDice().getValue()==3){
            test.start(player);
            test.setParameter(model.getState().getDraftPool().getCell(0));
        }
        test.setParameter(player.getWindowFrame());
        test.setParameter(player.getWindowFrame().getCell(3,3));

        assertFalse(model.getState().getDraftPool().getCell(0).isEmpty());
        assertTrue(player.getWindowFrame().getCell(3,3).isEmpty());
        assertTrue(test.hasNext());

        test.setParameter(player.getWindowFrame());
        test.setParameter(player.getWindowFrame().getCell(1,0));

        assertFalse(model.getState().getDraftPool().getCell(0).isEmpty());
        assertTrue(test.hasNext());

        test.setParameter(player.getWindowFrame());
        test.setParameter(player.getWindowFrame().getCell(0,1));

        assertFalse(player.getWindowFrame().getCell(0,1).isEmpty());
        assertTrue(model.getState().getDraftPool().getCell(0).isEmpty());
    }
}