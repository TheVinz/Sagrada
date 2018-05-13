package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.toolcards.Lathekin;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

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
        WindowFrame frame = new WindowFrame(WindowFrameList.AURORA_SAGRADIS);
        player=mock(Player.class);
        when(player.getWindowFrame()).thenReturn(frame);
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        toolCard.start(player);
        Dice dice=new Dice(Color.BLUE,4);
        Dice dice1=new Dice(Color.RED, 5);
        Dice dice2=new Dice(Color.PURPLE, 2);
        player.getWindowFrame().getCell(0,0).put(dice);
        player.getWindowFrame().getCell(1,0).put(dice1);
        player.getWindowFrame().getCell(2,2).put(dice2);

        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,1));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(3, 3));
        } catch(InvalidMoveException e){
            assertEquals("Choose two different dices", e.getMessage());
        }


        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,1));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        toolCard.setParameter(player.getWindowFrame());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(3, 1));
        } catch(InvalidMoveException e){
            assertEquals("Target positions must be different", e.getMessage());
        }


        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,1));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        toolCard.setParameter(new WindowFrame(WindowFrameList.FIRELIGHT));
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(3, 3));
        } catch(InvalidMoveException e){
            assertEquals("Dice must be on your same frame", e.getMessage());
        }

        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,3));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(3, 1));
        } catch(InvalidMoveException e){
            assertEquals("First move does not respect dice restrictions", e.getMessage());
        }



        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,2));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        toolCard.setParameter(player.getWindowFrame());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(3, 3));
        } catch(InvalidMoveException e){
            assertEquals("First move does not respect cell restrictions", e.getMessage());
        }


        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,1));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        toolCard.setParameter(player.getWindowFrame());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(0, 3));
        } catch(InvalidMoveException e){
            assertEquals("Second move does not respect dice restrictions", e.getMessage());
        }


        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,3));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(3, 2));
        } catch(InvalidMoveException e){
            assertEquals("Second move does not respect cell restrictions", e.getMessage());
        }

        player.getWindowFrame().getCell(3,1).put(new Dice(Color.BLUE));
        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,1));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        toolCard.setParameter(player.getWindowFrame());
        try {
            toolCard.setParameter(player.getWindowFrame().getCell(3, 3));
        } catch(InvalidMoveException e){
            assertEquals("Already filled cell", e.getMessage());
        }

        player.getWindowFrame().getCell(3,1).removeDice();


        toolCard.start(player);
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,1));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(1,0));
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(3,3));
        assertEquals(dice, player.getWindowFrame().getCell(3,1).getDice());
        assertEquals(dice1, player.getWindowFrame().getCell(3,3).getDice());

    }
}