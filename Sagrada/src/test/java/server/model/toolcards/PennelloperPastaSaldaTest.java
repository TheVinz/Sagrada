package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import server.GameManager;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.toolcards.PennelloperPastaSalda;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class PennelloperPastaSaldaTest {

    private Player player;
    private ToolCard toolCard;
    private Model model;
    private GameManager gameManager;

    @Before
    public void setUp() throws Exception {
        gameManager=new GameManager();
        model=Mockito.spy(new Model(gameManager));
        toolCard =new PennelloperPastaSalda(model);
        player=Mockito.mock(Player.class);
        WindowFrame frame=new WindowFrame(WindowFrameList.GRAVITAS);
        Mockito.when(player.getWindowFrame()).thenReturn(frame);

    }
    @Test
    public void shouldStart() throws InvalidMoveException {
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            assertEquals("Draft pool is empty",e.getMessage());
        }
        Mockito.when(player.isDiceMoved()).thenReturn(true);
        model.getState().getDraftPool().getCell(0).put(new Dice(Color.BLUE,4));
        try{
            toolCard.start(player);
        }catch(InvalidMoveException e){
            assertEquals("Can only place a dice once per turn",e.getMessage());
        }
    }

    @Test
    public void doAbility() throws Exception {
        model.getState().getDraftPool().getCell(0).put(new Dice(Color.BLUE,4));
        toolCard.start(player);
        assertEquals(Response.DRAFT_POOL_CELL,toolCard.next());
        try{
            try {
                toolCard.setParameter(player.getWindowFrame());
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch (InvalidMoveException e){
            assertEquals("Wrong parameter",e.getMessage());
        }
        try {
            toolCard.setParameter(model.getState().getDraftPool().getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertFalse(toolCard.hasNext());

        player.getWindowFrame().getCell(0,0).put(new Dice(Color.YELLOW, 3));
        player.getWindowFrame().getCell(1,0).put(new Dice(Color.YELLOW, 3));

        toolCard.start(player);
        try {
            toolCard.setParameter(model.getState().getDraftPool().getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        while(model.getState().getDraftPool().getCell(0).getDice().getValue()==3){
            toolCard.start(player);
            try {
                toolCard.setParameter(model.getState().getDraftPool().getCell(0));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        assertEquals(Response.WINDOW_FRAME,toolCard.next());
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertEquals(null,toolCard.next());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(3,3));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        assertFalse(model.getState().getDraftPool().getCell(0).isEmpty());
        assertTrue(player.getWindowFrame().getCell(3,3).isEmpty());
        assertTrue(toolCard.hasNext());

        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        assertFalse(model.getState().getDraftPool().getCell(0).isEmpty());
        assertTrue(toolCard.hasNext());

        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(0,1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        assertFalse(player.getWindowFrame().getCell(0,1).isEmpty());
        assertTrue(model.getState().getDraftPool().getCell(0).isEmpty());

        model.getState().getDraftPool().increaseSize();
        model.getState().getDraftPool().getCell(1).put(new Dice(Color.BLUE,4));
        toolCard.start(player);
       try{ try {
            toolCard.setParameter(model.getState().getDraftPool().getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
       }catch (InvalidMoveException e){
           assertEquals("PoolCell is empty",e.getMessage());
       }
    }
    @Test
    public void shouldGetNumber(){
        assertEquals(6, toolCard.getNumber());
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.PURPLE,toolCard.getColor());
    }
}