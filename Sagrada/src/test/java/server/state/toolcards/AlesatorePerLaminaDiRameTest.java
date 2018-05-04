package server.state.toolcards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.boards.windowframe.WindowFrameList;
import server.state.dice.Dice;
import server.state.player.Player;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class AlesatorePerLaminaDiRameTest {

    private Model model;
    private ToolCard test;
    private Player player;

    @Before
    public void setUp() throws Exception {
        model= Mockito.mock(Model.class);
        player=Mockito.mock(Player.class);
        test=new AlesatorePerLaminaDiRame(model);
        player.getWindowFrame().getCell(0,0).put(new Dice(Color.RED, 4));
        player.getWindowFrame().getCell(0,2).put(new Dice(Color.BLUE, 5));
    }

    @Test
    public void start() throws InvalidMoveException {
        test.start(player);
    }

    @Test
    public void doAbility() throws InvalidMoveException {
        test.start(player);
        test.setParameter(WindowFrame.AURORA_SAGRADIS);
        test.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0, 0));
        test.setParameter(WindowFrame.AURORA_SAGRADIS);
        test.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0, 1));

        test.start(player);
        test.setParameter(WindowFrame.FIRELIGHT);
        test.setParameter(WindowFrame.FIRELIGHT.getCell(0, 0));
        test.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            test.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0, 1));
        }
        catch(InvalidMoveException e){
            assertEquals("Wrong parameter", e.getMessage());
        }

        test.start(player);
        test.setParameter(WindowFrame.AURORA_SAGRADIS);
        test.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0, 0));
        test.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            test.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0, 4));
        }catch(InvalidMoveException e){
            assertEquals("Cell and dice colors must be equal", e.getMessage());
        }

        test.start(player);
        test.setParameter(WindowFrame.AURORA_SAGRADIS);
        test.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(0, 0));
        test.setParameter(WindowFrame.AURORA_SAGRADIS);
        try {
            test.setParameter(WindowFrame.AURORA_SAGRADIS.getCell(3, 0));
        }catch(InvalidMoveException e){
            assertEquals("Invalid adjacent dices", e.getMessage());
        }
    }

    @Test
    public void setParameter() throws InvalidMoveException {
        test.start(player);
        try{
            test.setParameter(WindowFrame.AURORA_SAGRADIS);
        }
        catch (InvalidMoveException e){
            assert false;
        }
        try{
            test.setParameter(WindowFrame.AURORA_SAGRADIS);
        }
        catch(InvalidMoveException e){
            assertEquals("Wrong parameter", e.getMessage());
        }
    }
}