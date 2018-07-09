package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import server.GameManager;
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
    private GameManager gameManager;


    @Before
    public void setUp() throws Exception {
        gameManager = Mockito.mock(GameManager.class);
        model = Mockito.spy(new Model(gameManager));
        player = Mockito.mock(Player.class);
        toolCard = new TenagliaARotelle(model);
        draftPool = model.getState().getDraftPool();
        Mockito.when(player.getWindowFrame()).thenReturn(new WindowFrame(WindowFrameList.AURORA_SAGRADIS));
        windowFrame = player.getWindowFrame();
        windowFrame.getCell(0, 0).put(new Dice(Color.RED, 4));
        windowFrame.getCell(0, 2).put(new Dice(Color.BLUE, 5));
        draftPool = model.getState().getDraftPool();
        draftPool.increaseSize();



    }

    @Test
    public void shouldStart() throws InvalidMoveException {
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            assertEquals("Draft pool is empty",e.getMessage());
        }
        draftPool.getCell(0).put(new Dice(Color.BLUE,3));

        Mockito.when(player.isSecondTurn()).thenReturn(true);
        try {
            toolCard.start(player);
            fail("should be exception");
        } catch (InvalidMoveException e) {
            assertEquals("Only during your first turn", e.getMessage());
        }
        Mockito.when(player.isSecondTurn()).thenReturn(false);
        try{
            toolCard.start(player);
        }catch (InvalidMoveException e){
            assertEquals("You have to place a dice first",e.getMessage());
        }
    }
    @Test
    public void shouldDoAbility() throws InvalidMoveException {
        draftPool.getCell(0).put(new Dice(Color.YELLOW, 6));
        draftPool.getCell(2).put(new Dice(Color.YELLOW, 4));
        Mockito.when(player.isDiceMoved()).thenReturn(true);
        Mockito.when(player.isSecondTurn()).thenReturn(false);

            toolCard.start(player);
            assertEquals(Response.DRAFT_POOL_MOVE,toolCard.next());

        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertEquals(null,toolCard.next());
        try {
            toolCard.setParameter(windowFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(windowFrame.getCell(0,0));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
            fail("should be exception");}
        catch (InvalidMoveException e){
            assertEquals("Move does not respect restrictions",e.getMessage());
        }
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            fail("shouldn't be exception");
        }
        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(windowFrame.getCell(0,1));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        catch (InvalidMoveException e){
            fail("shouldn't be exception");
        }

        toolCard.start(player);
            try {
                toolCard.setParameter(draftPool.getCell(1));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }

        try {
            toolCard.setParameter(windowFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{try {
            toolCard.setParameter(windowFrame.getCell(0,0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        }catch (InvalidMoveException e){
            assertEquals("PoolCell is empty",e.getMessage());
        }
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.RED,toolCard.getColor());
    }
    @Test
    public void shouldGetNumber(){
        assertEquals(8,toolCard.getNumber());
    }
}


