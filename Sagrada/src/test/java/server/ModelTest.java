package server;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.Color;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ModelTest {

    private Model model;
    private Player player;

    @Before
    public void setUp() throws Exception {
        model=new Model();
        player=mock(Player.class);
    }

    @Test
    public void addPlayer() throws Exception {
        assertEquals(1, model.getState().getDraftPool().getSize());
        ViewProxy view= model.addPlayer("Vinz");
        assertEquals(3, model.getState().getDraftPool().getSize());
        model.removeObserver(view);
    }

    @Test
    public void exchange() throws InvalidMoveException {
        WindowFrame.AURORA_SAGRADIS.clean();
        Dice dice= new Dice(Color.BLUE, 3), otherDice=new Dice(Color.GREEN, 6);
        WindowFrame.AURORA_SAGRADIS.getCell(0,0).put(dice);
        WindowFrame.AURORA_SAGRADIS.getCell(0,1).put(otherDice);
        model.exchange(player, WindowFrame.AURORA_SAGRADIS.getCell(0,0), WindowFrame.AURORA_SAGRADIS.getCell(0,1));
        assertEquals(dice, WindowFrame.AURORA_SAGRADIS.getCell(0,1).getDice());
        assertEquals(otherDice, WindowFrame.AURORA_SAGRADIS.getCell(0,0).getDice());
    }

    @Test
    public void increase() throws InvalidMoveException {
        WindowFrame.AURORA_SAGRADIS.clean();
        Dice dice= new Dice(Color.BLUE, 3);
        WindowFrame.AURORA_SAGRADIS.getCell(0,0).put(dice);
        model.increase(player, WindowFrame.AURORA_SAGRADIS.getCell(0,0));
    }

    @Test
    public void decrease() throws InvalidMoveException {
        WindowFrame.AURORA_SAGRADIS.clean();
        Dice dice= new Dice(Color.BLUE, 3);
        WindowFrame.AURORA_SAGRADIS.getCell(0,0).put(dice);
        model.decrease(player, WindowFrame.AURORA_SAGRADIS.getCell(0,0));
    }

    @Test
    public void drawDice() throws InvalidMoveException {
        WindowFrame.AURORA_SAGRADIS.clean();
        WindowFrame.AURORA_SAGRADIS.getCell(0,0).put(model.drawDice(player));
    }

    @Test
    public void flipDice() throws InvalidMoveException {
        WindowFrame.AURORA_SAGRADIS.clean();
        Dice dice= new Dice(Color.BLUE, 3);
        WindowFrame.AURORA_SAGRADIS.getCell(0,0).put(dice);
        model.flipDice(player, WindowFrame.AURORA_SAGRADIS.getCell(0,0));
        assertEquals(4, WindowFrame.AURORA_SAGRADIS.getCell(0,0).getDice().getValue());
    }

    @Test
    public void toolCardUsed() {
    }
}