package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.player.Player;
import server.Model;
import server.state.utilities.Color;

import javax.tools.Tool;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class LathekinTest {

    private Player player;
    private ToolCard toolCard;

    @Before
    public void setUp() throws Exception {
        Model model= spy(new Model());
        toolCard=new Lathekin(model);
        WindowFrame.AURORA_SAGRADIS.clean();
        player=mock(Player.class);
        when(player.getWindowFrame()).thenReturn(WindowFrame.AURORA_SAGRADIS);
    }

    @After
    public void tearDown() throws Exception {
        WindowFrame.AURORA_SAGRADIS.clean();
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        toolCard.start(player);
        Dice dice=new Dice(Color.BLUE,4);
        Dice dice1=new Dice(Color.RED, 5);
        Dice dice2=new Dice(Color.PURPLE, 2);
        WindowFrame.AURORA_SAGRADIS.getCell(0,0).put(dice);
        WindowFrame.AURORA_SAGRADIS.getCell(1,0).put(dice1);
        WindowFrame.AURORA_SAGRADIS.getCell(2,2).put(dice2);

        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,1));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3, 3));
        } catch(InvalidMoveException e){
            assertEquals("Choose two different dices", e.getMessage());
        }


        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,1));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(1,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3, 1));
        } catch(InvalidMoveException e){
            assertEquals("Target positions must be different", e.getMessage());
        }


        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,1));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(1,0));
        toolCard.setParameter(WindowFrame.FIRELIGHT);
        try {
            toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3, 3));
        } catch(InvalidMoveException e){
            assertEquals("Dice must be on your same frame", e.getMessage());
        }

        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(1,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,3));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3, 1));
        } catch(InvalidMoveException e){
            assertEquals("First move does not respect dice restrictions", e.getMessage());
        }



        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,2));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(1,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3, 3));
        } catch(InvalidMoveException e){
            assertEquals("First move does not respect cell restrictions", e.getMessage());
        }


        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,1));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(1,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0, 3));
        } catch(InvalidMoveException e){
            assertEquals("Second move does not respect dice restrictions", e.getMessage());
        }


        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(1,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,3));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3, 2));
        } catch(InvalidMoveException e){
            assertEquals("Second move does not respect cell restrictions", e.getMessage());
        }

        WindowFrame.AURORA_SAGRADIS.getCell(3,1).put(new Dice(Color.BLUE));
        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,1));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(1,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3, 3));
        } catch(InvalidMoveException e){
            assertEquals("Already filled cell", e.getMessage());
        }

        WindowFrame.AURORA_SAGRADIS.getCell(3,1).removeDice();


        toolCard.start(player);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,1));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(1,0));
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3,3));
        assertEquals(dice, WindowFrame.AURORA_SAGRADIS.getCell(3,1).getDice());
        assertEquals(dice1, WindowFrame.AURORA_SAGRADIS.getCell(3,3).getDice());

    }
}