package server.state.toolcards;

import common.exceptions.InvalidMoveException;
//import javafx.print.PageLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.state.boards.draftpool.DraftPool;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.dice.Dice;
import server.state.player.Player;
import server.Model;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

public class MartellettoTest {

    private ToolCard toolCard;
    private Player player;
    private List<DraftPoolCell> draftPool;
    private Model model;

    @Before
    public void setUp() throws Exception {
        model=new Model();
        player=mock(Player.class);
        Mockito.when(player.isSecondTurn()).thenReturn(true);
        model.addPlayer("TheVinz");
        model.addPlayer("Strenuus");
        model.addPlayer("GabStuc");
        model.getState().getDraftPool().draw(model.getState().getBag());
        model.getState().getDraftPool().getCell(2).removeDice();
        draftPool=model.getState().getDraftPool().getDraftPool();
        toolCard=new Martelletto(model);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        toolCard.start(player);
        for(int i=0; i<draftPool.size(); i++){
            if(draftPool.get(i).isEmpty())
                assertNull(model.getState().getDraftPool().getCell(i).getDice());
            else
                assertEquals(draftPool.get(i).getDice().getColor(),model.getState().getDraftPool().getCell(i).getDice().getColor());
        }
        assertFalse(toolCard.hasNext());
    }
}