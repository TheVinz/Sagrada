package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Model;
import server.state.State;
import server.state.bag.Bag;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.Color;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static server.state.utilities.Color.*;

public class DiluentePerPastaSaldaTest {

    private Player player;
    private ToolCard toolCard;

    @Before
    public void setUp(){
        player=mock(Player.class);
        WindowFrame frame= new WindowFrame(WindowFrameList.AURORA_SAGRADIS);
        Model model= spy(new Model());
        when(model.drawDice(player)).thenReturn(new Dice(YELLOW));
        toolCard=new DiluentePerPastaSalda(model);
        when(player.getWindowFrame()).thenReturn(frame);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void doAbility() throws InvalidMoveException {


        DraftPoolCell draftPoolCell=new DraftPoolCell();
        draftPoolCell.put(new Dice(Color.BLUE, 3));
        player.getWindowFrame().getCell(0,0).put(new Dice(YELLOW, 6));
        player.getWindowFrame().getCell(2,1).put(new Dice(RED, 6));
        player.getWindowFrame().getCell(2,0).put(new Dice(RED,6));

        //Yellow 6 in 0 0
        //Red 6 in 2 1
        //Red 6 in 2 0
        //Blue 3 in DraftPool[0]

        toolCard.start(player);
        toolCard.setParameter(draftPoolCell);
        toolCard.setParameter(1);

        //expected Yellow 1 in DraftPool[0]

        assertEquals(YELLOW, draftPoolCell.getDice().getColor());
        assertEquals(1, draftPoolCell.getDice().getValue());

        //invalid adjacent dices for 0 4
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(0,4));

        //expected invalid move without exception
        //move ignored

        assertFalse(draftPoolCell.isEmpty());
        assertEquals(YELLOW, draftPoolCell.getDice().getColor());
        assertEquals(1, draftPoolCell.getDice().getValue());
        assertTrue(player.getWindowFrame().getCell(0,4).isEmpty());

        //Already filled cell 2 1
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter( player.getWindowFrame().getCell(2,1));

        //move ignored

        assertFalse(draftPoolCell.isEmpty());

        //should be a valid cell 2 2
        toolCard.setParameter(player.getWindowFrame());
        toolCard.setParameter(player.getWindowFrame().getCell(2, 2));


        assertNotNull(player.getWindowFrame().getCell(2,2).getDice());
        assertEquals(1, player.getWindowFrame().getCell(2,2).getDice().getValue());
        assertTrue(draftPoolCell.isEmpty());

        //move done successfully
        //Yellow 1 in 2 2
        //restart

        player.getWindowFrame().getCell(3,2).put(new Dice(YELLOW,6));
        //Yellow 6 in 3 2
        toolCard.start(player);

        //purple dice in DP[0]

        draftPoolCell.put(new Dice(YELLOW));
        toolCard.setParameter(draftPoolCell);
        toolCard.setParameter(6);

        //Yellow 6 in DP[0]
        //should have no valid cell for Yellow 6 on WF

        assertFalse(toolCard.hasNext());
    }

}