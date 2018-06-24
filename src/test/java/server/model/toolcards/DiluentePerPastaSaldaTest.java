package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.GameManager;
import server.model.Model;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.DiluentePerPastaSalda;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Choice;
import server.model.state.utilities.Color;

import java.rmi.RemoteException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static server.model.state.utilities.Color.*;

public class DiluentePerPastaSaldaTest {

    private Player player;
    private ToolCard toolCard;
    private DraftPool draftPool;
    private GameManager gameManager;

    @Before
    public void setUp() throws Exception {
        gameManager=new GameManager();
        player=mock(Player.class);
        WindowFrame frame= new WindowFrame(WindowFrameList.AURORA_SAGRADIS);
        Model model= spy(new Model(gameManager));
        when(model.drawDice(player)).thenReturn(new Dice(YELLOW));
        toolCard=new DiluentePerPastaSalda(model);
        when(player.getWindowFrame()).thenReturn(frame);
        draftPool = model.getState().getDraftPool();
        draftPool.increaseSize();

    }

    @SuppressWarnings("Duplicates")
    @Test
    public void doAbility() throws InvalidMoveException {
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            assertEquals("Draft pool is empty",e.getMessage());
        }

        draftPool.getCell(1).put(new Dice(Color.BLUE,4));
        WindowFrame secondFrame = new WindowFrame(WindowFrameList.AURORAE_MAGNIFICUS);
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
        toolCard.start(player);
        try {
            toolCard.setParameter(draftPool.getCell(1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(new Choice(1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        draftPool.getCell(0).put(new Dice(Color.BLUE,3));
        player.getWindowFrame().getCell(0,0).put(new Dice(YELLOW, 6));
        player.getWindowFrame().getCell(2,1).put(new Dice(RED, 6));
        player.getWindowFrame().getCell(2,0).put(new Dice(RED,6));
        //Yellow 6 in 0 0
        //Red 6 in 2 1
        //Red 6 in 2 0
        //Blue 3 in DraftPool[0]
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        assertEquals(true,toolCard.hasNext());
        assertEquals(Response.DRAFT_POOL_CELL,toolCard.next());
            try {
                try {
                    toolCard.setParameter(draftPool.getCell(0));
                } catch (WrongParameter wrongParameter) {
                    wrongParameter.printStackTrace();
                }
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        assertEquals(Response.DILUENTE_PER_PASTA_SALDA_CHOICE,toolCard.next());
        try {
            try {
                toolCard.setParameter(new Choice(1));
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertEquals(Response.WINDOW_FRAME,toolCard.next());
        //expected Yellow 1 in DraftPool[0]
        assertEquals(YELLOW, draftPool.getCell(0).getDice().getColor());
        assertEquals(1, draftPool.getCell(0).getDice().getValue());
        //invalid adjacent dices for 0 4
        try {
            try {
                toolCard.setParameter(player.getWindowFrame());
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(0,4));
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        //expected invalid move without exception
        //move ignored

        assertFalse(draftPool.getCell(0).isEmpty());
        assertEquals(YELLOW, draftPool.getCell(0).getDice().getColor());
        assertEquals(1, draftPool.getCell(0).getDice().getValue());
        assertTrue(player.getWindowFrame().getCell(0,4).isEmpty());

        //Already filled cell 2 1
        try {
            try {
                toolCard.setParameter(player.getWindowFrame());
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                toolCard.setParameter( player.getWindowFrame().getCell(2,1));
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        //move ignored

        assertFalse(draftPool.getCell(0).isEmpty());

        //should be a valid cell 2 2
        try {
            try {
                toolCard.setParameter(player.getWindowFrame());
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertEquals(null,toolCard.next());
        try {
            try {
                toolCard.setParameter(player.getWindowFrame().getCell(2, 2));
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }


        assertNotNull(player.getWindowFrame().getCell(2,2).getDice());
        assertEquals(1, player.getWindowFrame().getCell(2,2).getDice().getValue());
        assertTrue(draftPool.getCell(0).isEmpty());

        //move done successfully
        //Yellow 1 in 2 2
        //restart

        //Yellow 6 in 3 2

        try {
            draftPool.getCell(0).put(new Dice(YELLOW));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        //yellow dice in DP[0]
        try {
            try {
                toolCard.setParameter(draftPool.getCell(1));
            } catch (InvalidMoveException e) {
                assertEquals("PoolCell is empty",e.getMessage());
            }
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        //draftPoolCell empty
        when(player.isDiceMoved()).thenReturn(true);
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
           assertEquals("You can only place a dice once per turn",e.getMessage());
        }

    }

    @Test
    public void shouldGetNumber() {
        assertEquals(11,toolCard.getNumber());
    }

    @Test
    public void shouldGetColor() {
        assertEquals(Color.PURPLE,toolCard.getColor());
    }
}
