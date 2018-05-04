package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class RigaInSugheroTest {

    private Player player;
    private ToolCard toolCard;
    private Model model;
    @Before
    public void setUp() throws Exception {
        model=Mockito.spy(new Model());
        toolCard=new RigaInSughero(model);
        player=Mockito.mock(Player.class);
        Mockito.when(player.getWindowFrame()).thenReturn(new WindowFrame(WindowFrameList.GRAVITAS));
    }
    @Test
    public void doAbilityTest() throws Exception{
        model.getState().getDraftPool().getCell(0).put(new Dice(Color.BLUE,4));
        player.getWindowFrame().getCell(0,0).put(new Dice(Color.BLUE, 1));
        toolCard.start(player);
        toolCard.setParameter(model.getState().getDraftPool().getCell(0));
        toolCard.setParameter(player.getWindowFrame());
        try{
            toolCard.setParameter(player.getWindowFrame().getCell(0,4));
        }
        catch (InvalidMoveException e)
        {
            fail("Shouldn't be exception");

        }

        model.getState().getDraftPool().getCell(0).put(new Dice(Color.BLUE,4));
        toolCard.start(player);
        toolCard.setParameter(model.getState().getDraftPool().getCell(0));
        toolCard.setParameter(player.getWindowFrame());
        try{
            toolCard.setParameter(player.getWindowFrame().getCell(0,1));
            fail("Should be exception");
        }
        catch (InvalidMoveException e)
        {
            assertEquals("Cell must have no adjacent dices", e.getMessage());
        }

        toolCard.start(player);
        toolCard.setParameter(model.getState().getDraftPool().getCell(0));
        toolCard.setParameter(player.getWindowFrame());
        try{
            toolCard.setParameter(player.getWindowFrame().getCell(0,2));
            fail("Should be exception");
        }
        catch (InvalidMoveException e)
        {
            assertEquals("Placement must respect cell restrictions", e.getMessage());
        }

    }
}