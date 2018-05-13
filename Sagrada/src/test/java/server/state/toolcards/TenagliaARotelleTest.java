package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import server.model.Model;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.TenagliaARotelle;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;


import static org.junit.Assert.*;

public class TenagliaARotelleTest {
    private Model model;
    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool;
    private WindowFrame windowFrame;


    @Before
    public void setUp() throws Exception {
        model = Mockito.spy(new Model());
        player = Mockito.mock(Player.class);
        toolCard = new TenagliaARotelle(model);
        draftPool = model.getState().getDraftPool();
        Mockito.when(player.getWindowFrame()).thenReturn(new WindowFrame(WindowFrameList.AURORA_SAGRADIS));
        windowFrame = player.getWindowFrame();
        windowFrame.getCell(0, 0).put(new Dice(Color.RED, 4));
        windowFrame.getCell(0, 2).put(new Dice(Color.BLUE, 5));
        draftPool = new DraftPool();
        draftPool.increaseSize();
        draftPool.getCell(0).put(new Dice(Color.YELLOW, 6));
        draftPool.getCell(1).put(new Dice(Color.GREEN, 5));
        draftPool.getCell(2).put(new Dice(Color.YELLOW, 4));


    }

    @Test
    public void shouldStart() throws InvalidMoveException {

        Mockito.when(player.isFirstMoveDone()).thenReturn(true);
        try {
            toolCard.start(player);
            fail("should be exception");
        } catch (InvalidMoveException e) {
            assertEquals("Only during your first turn", e.getMessage());
        }
    }

    @Test
    public void shouldDoAbility() throws InvalidMoveException {
        Mockito.when(player.isFirstMoveDone()).thenReturn(false);
        try {
            toolCard.start(player);

        } catch (InvalidMoveException e) {
            fail("shouldn't be exception");
        }
        toolCard.setParameter(draftPool.getCell(0));
        toolCard.setParameter(windowFrame);
        try{toolCard.setParameter(windowFrame.getCell(0,0));
            fail("should be exception");}
        catch (InvalidMoveException e){
            assertEquals("Move does not respect restrictions",e.getMessage());
        }

        try {
            toolCard.start(player);

        } catch (InvalidMoveException e) {
            fail("shouldn't be exception");
        }
        toolCard.setParameter(draftPool.getCell(0));
        toolCard.setParameter(windowFrame);
        try{toolCard.setParameter(windowFrame.getCell(0,1));
        }
        catch (InvalidMoveException e){
            fail("shouldn't be exception");
        }




    }
}


