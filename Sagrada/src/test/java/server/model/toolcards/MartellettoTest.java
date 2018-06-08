package server.model.toolcards;

import common.exceptions.InvalidMoveException;
//import javafx.print.PageLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.toolcards.Martelletto;
import server.model.state.toolcards.ToolCard;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

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
      //  model.addRMIPlayer("TheVinz");
        //model.addRMIPlayer("Strenuus");
       // model.addRMIPlayer("GabStuc");
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
        try {
            toolCard.start(player);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        for(int i=0; i<draftPool.size(); i++){
            if(draftPool.get(i).isEmpty())
                assertNull(model.getState().getDraftPool().getCell(i).getDice());
            else
                assertEquals(draftPool.get(i).getDice().getColor(),model.getState().getDraftPool().getCell(i).getDice().getColor());
        }
        assertFalse(toolCard.hasNext());
    }
}