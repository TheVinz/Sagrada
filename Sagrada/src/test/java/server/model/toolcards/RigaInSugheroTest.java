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
import server.model.state.toolcards.RigaInSughero;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class RigaInSugheroTest {

    private Player player;
    private ToolCard toolCard;
    private Model model;
    private DraftPool draftPool;
    private WindowFrame secondFrame;
    private GameManager gameManager;
    @Before
    public void setUp() throws Exception {
        gameManager=new GameManager();
        model=Mockito.spy(new Model(gameManager));
        toolCard=new RigaInSughero(model);
        player=Mockito.mock(Player.class);
        Mockito.when(player.getWindowFrame()).thenReturn(new WindowFrame(WindowFrameList.GRAVITAS));
        draftPool=model.getState().getDraftPool();
        draftPool.increaseSize();
        secondFrame = new WindowFrame(WindowFrameList.AURORAE_MAGNIFICUS);
        secondFrame.getCell(0,0).put(new Dice(Color.RED,5));
        secondFrame.getCell(0,1).put(new Dice(Color.GREEN,1));
        secondFrame.getCell(0,2).put(new Dice(Color.BLUE,5));
        secondFrame.getCell(0,3).put(new Dice(Color.PURPLE,1));
        secondFrame.getCell(0,4).put(new Dice(Color.RED,2));
        secondFrame.getCell(1,0).put(new Dice(Color.PURPLE,1));
        secondFrame.getCell(1,1).put(new Dice(Color.RED,5));
        secondFrame.getCell(1,2).put(new Dice(Color.YELLOW,1));
        secondFrame.getCell(1,3).put(new Dice(Color.RED,5));
        secondFrame.getCell(1,4).put(new Dice(Color.YELLOW,1));
        secondFrame.getCell(2,0).put(new Dice(Color.YELLOW,5));
        secondFrame.getCell(2,1).put(new Dice(Color.GREEN,2));
        secondFrame.getCell(2,2).put(new Dice(Color.RED,6));
        secondFrame.getCell(2,3).put(new Dice(Color.BLUE,3));
        secondFrame.getCell(2,4).put(new Dice(Color.PURPLE,5));
        secondFrame.getCell(3,0).put(new Dice(Color.RED,1));
        secondFrame.getCell(3,1).put(new Dice(Color.BLUE,4));
        secondFrame.getCell(3,2).put(new Dice(Color.PURPLE,5));
        secondFrame.getCell(3,3).put(new Dice(Color.GREEN,1));
         secondFrame.getCell(3,4).put(new Dice(Color.BLUE,4));
    }
    @Test
    public void shouldStart() throws InvalidMoveException {
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            assertEquals("You still have to place your first dice",e.getMessage());
            }
        Mockito.when(player.isFirstMoveDone()).thenReturn(true);
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            assertEquals("Draft pool is empty",e.getMessage());
        }
        draftPool.getCell(0).put(new Dice(Color.BLUE,4));
        Mockito.when(player.isDiceMoved()).thenReturn(true);
       try {
           toolCard.start(player);
       }catch (InvalidMoveException e){
           assertEquals("You can only place a dice once per turn",e.getMessage());
       }
        Mockito.when(player.getWindowFrame()).thenReturn(secondFrame);

       try{
           toolCard.start(player);
       }catch (InvalidMoveException e){
           assertEquals("No available moves",e.getMessage());
       }
    }
    @Test
    public void doAbilityTest() throws InvalidMoveException {
        draftPool.getCell(0).put(new Dice(Color.BLUE,4));
        player.getWindowFrame().getCell(0,0).put(new Dice(Color.BLUE, 1));
        Mockito.when(player.isFirstMoveDone()).thenReturn(true);
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertEquals(Response.DRAFT_POOL_MOVE,toolCard.next());
        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertEquals(null,toolCard.next());
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(0,4));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        catch (InvalidMoveException e)
        {
            fail("Shouldn't be exception");
        }

        draftPool.getCell(0).put(new Dice(Color.BLUE,4));
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(0,1));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
            fail("Should be exception");
        }
        catch (InvalidMoveException e)
        {
            assertEquals("Cell must have no adjacent dices", e.getMessage());
        }

        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        try {
            toolCard.setParameter(draftPool.getCell(0));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(0,2));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
            fail("Should be exception");
        }
        catch (InvalidMoveException e)
        {
            assertEquals("Placement must respect cell restrictions", e.getMessage());
        }
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        try {
            toolCard.setParameter(draftPool.getCell(2));
        } catch (InvalidMoveException e) {
          e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try{
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(0,0));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch (InvalidMoveException e){
            assertEquals("PoolCell is empty",e.getMessage());
        }
    }
    @Test
    public void shouldGetNumber(){
        assertEquals(9,toolCard.getNumber());
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.YELLOW,toolCard.getColor());
    }
}