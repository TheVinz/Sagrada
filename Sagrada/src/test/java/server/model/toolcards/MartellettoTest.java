package server.model.toolcards;

import common.exceptions.InvalidMoveException;
//import javafx.print.PageLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.toolcards.Martelletto;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MartellettoTest {

    private ToolCard toolCard;
    private Player player;
    private DraftPool draftPool;
    private Model model;
    @Before
    public void setUp() throws Exception {
        model=new Model();
        player=mock(Player.class);
        draftPool = model.getState().getDraftPool();
        draftPool.increaseSize();
        toolCard=new Martelletto(model);}
    @Test
    public void doAbility()  {
        try {
            try {
                toolCard.start(player);
            } catch (InvalidMoveException e) {    //drafpool vuota
                e.printStackTrace();
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        try {
            draftPool.getCell(1).put(new Dice(Color.BLUE,3));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {     //non è la seconda mossa del giocatore
            e.printStackTrace();
        }
        Mockito.when(player.isSecondTurn()).thenReturn(true);
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        for(int i=0; i<draftPool.getSize(); i++){
            if(draftPool.getCell(i).isEmpty())
                assertNull(draftPool.getCell(i).getDice());    //andata a buon fine
            else
                assertEquals(Color.BLUE,draftPool.getCell(i).getDice().getColor());
        }
        assertFalse(toolCard.hasNext());
        Mockito.when(player.isDiceMoved()).thenReturn(true);
        try {
            toolCard.start(player);
        } catch (InvalidMoveException e) {     //il giocatore ha già mosso un dado
            e.printStackTrace();
        }
    }
    @Test
    public void shouldGetNumber(){
        assertEquals(7,toolCard.getNumber());
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.BLUE,toolCard.getColor());
    }
}