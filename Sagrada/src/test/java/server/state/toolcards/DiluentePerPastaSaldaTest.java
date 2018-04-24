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
        WindowFrame.AURORA_SAGRADIS.clean();
        Model model= spy(new Model());
        when(model.drawDice(player)).thenReturn(new Dice(YELLOW));
        toolCard=new DiluentePerPastaSalda(model);
        when(player.getWindowFrame()).thenReturn(WindowFrame.AURORA_SAGRADIS);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void doAbility() throws InvalidMoveException {
        DraftPoolCell draftPoolCell=new DraftPoolCell();
        WindowFrame.AURORA_SAGRADIS.clean();
        draftPoolCell.put(new Dice(Color.BLUE, 3));
        WindowFrame.AURORA_SAGRADIS.getCell(0,0).put(new Dice(YELLOW, 6));
        WindowFrame.AURORA_SAGRADIS.getCell(2,1).put(new Dice(RED, 6));
        WindowFrame.AURORA_SAGRADIS.getCell(2,0).put(new Dice(RED,6));
        toolCard.start(player);
        toolCard.setParameter(draftPoolCell);
        toolCard.setParameter(1);
        assertEquals(YELLOW, draftPoolCell.getDice().getColor());
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0,4));
        assertFalse(draftPoolCell.isEmpty());
        assertEquals(YELLOW, draftPoolCell.getDice().getColor());
        assertTrue(WindowFrame.AURORA_SAGRADIS.getCell(0,4).isEmpty());
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter( WindowFrame.AURORA_SAGRADIS.getCell(2,1));
        assertFalse(draftPoolCell.isEmpty());
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS);
        toolCard.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(2, 2));
        assertNotNull(WindowFrame.AURORA_SAGRADIS.getCell(2,2).getDice());
        assertEquals(1, WindowFrame.AURORA_SAGRADIS.getCell(2,2).getDice().getValue());
        assertTrue(draftPoolCell.isEmpty());

        WindowFrame.AURORA_SAGRADIS.clean();
        toolCard.start(player);
        draftPoolCell.put(new Dice(PURPLE));
        toolCard.setParameter(draftPoolCell);
        toolCard.setParameter(2);
        assertFalse(toolCard.hasNext());
    }

    @After
    public void after(){
        WindowFrame.AURORA_SAGRADIS.clean();
    }

}
