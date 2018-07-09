package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.GameManager;
import server.model.Model;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.AlesatorePerLaminaDiRame;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AlesatorePerLaminaDiRameTest {

    private Model model;
    private ToolCard toolCard;
    private Player player;
    private GameManager gameManager;
    @Before
    public void setUp() throws Exception {
        gameManager = Mockito.mock(GameManager.class);
        model= Mockito.spy(new Model(gameManager));
        player=Mockito.mock(Player.class);
        WindowFrame frame = new WindowFrame(WindowFrameList.AURORA_SAGRADIS);
        when(player.getWindowFrame()).thenReturn(frame);
        toolCard =new AlesatorePerLaminaDiRame(model);
        player.getWindowFrame().getCell(0,0).put(new Dice(Color.RED, 4));
        player.getWindowFrame().getCell(0,2).put(new Dice(Color.BLUE, 5));
    }

    @Test
    public void start() throws InvalidMoveException {
        toolCard.start(player);
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        toolCard.start(player);
        assertEquals(Response.WINDOW_FRAME_MOVE,toolCard.next());
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(0, 0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertEquals(null,toolCard.next());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(0, 1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }




        WindowFrame secondFrame=new WindowFrame(WindowFrameList.FIRELIGHT);
        toolCard.start(player);
        try {
            toolCard.setParameter(secondFrame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(secondFrame.getCell(0, 0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(0, 1));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        catch(InvalidMoveException e){
            assertEquals("Wrong parameter", e.getMessage());
        }

        player.getWindowFrame().getCell(0,0).put(new Dice(Color.RED, 4));
        toolCard.start(player);
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(0, 0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(0, 4));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch(InvalidMoveException e){
            assertEquals("Cell and dice colors must be equal", e.getMessage());
        }

        toolCard.start(player);
        System.out.println(player.getWindowFrame().getCell(0,0).getDice().getColor());
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(0, 0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(3, 0));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch(InvalidMoveException e){
            assertEquals("Invalid adjacent dices", e.getMessage());
        }
    }

    @Test
    public void setParameter() throws InvalidMoveException {
        toolCard.start(player);
        try{
            try {
                toolCard.setParameter(player.getWindowFrame());
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        catch (InvalidMoveException e){
            assert false;
        }
        try{
            try {
                toolCard.setParameter(player.getWindowFrame());
            } catch (WrongParameter wrongParameter) {
                assertEquals("This exception should never be thrown in GUI (Error in ToolCard.java)", wrongParameter.getMessage());
            }
        }
        catch(InvalidMoveException e){
            assertEquals("Wrong parameter", e.getMessage());
        }
    }
    @Test
    public void shouldGetNumber(){
        assertEquals(3, toolCard.getNumber());
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.RED,toolCard.getColor());
    }
}