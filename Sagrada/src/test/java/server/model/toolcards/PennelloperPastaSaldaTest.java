package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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
    private ToolCard test;
    private Model model;

    @Before
    public void setUp() throws Exception {
        model=Mockito.spy(new Model());
        test=new PennelloperPastaSalda(model);
        player=Mockito.mock(Player.class);
        WindowFrame frame=new WindowFrame(WindowFrameList.GRAVITAS);
        Mockito.when(player.getWindowFrame()).thenReturn(frame);
        model.getState().getDraftPool().getCell(0).put(new Dice(Color.BLUE,4));
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        test.start(player);
        try {
            test.setParameter(model.getState().getDraftPool().getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        assertFalse(test.hasNext());

        player.getWindowFrame().getCell(0,0).put(new Dice(Color.YELLOW, 3));
        player.getWindowFrame().getCell(1,0).put(new Dice(Color.YELLOW, 3));

        test.start(player);
        try {
            test.setParameter(model.getState().getDraftPool().getCell(0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        while(model.getState().getDraftPool().getCell(0).getDice().getValue()==3){
            test.start(player);
            try {
                test.setParameter(model.getState().getDraftPool().getCell(0));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(3,3));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        assertFalse(model.getState().getDraftPool().getCell(0).isEmpty());
        assertTrue(player.getWindowFrame().getCell(3,3).isEmpty());
        assertTrue(test.hasNext());

        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(1,0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        assertFalse(model.getState().getDraftPool().getCell(0).isEmpty());
        assertTrue(test.hasNext());

        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(0,1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        assertFalse(player.getWindowFrame().getCell(0,1).isEmpty());
        assertTrue(model.getState().getDraftPool().getCell(0).isEmpty());
    }
}