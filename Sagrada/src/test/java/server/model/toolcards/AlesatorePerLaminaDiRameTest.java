package server.model.toolcards;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import server.model.Model;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.AlesatorePerLaminaDiRame;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AlesatorePerLaminaDiRameTest {

    private Model model;
    private ToolCard test;
    private Player player;

    @Before
    public void setUp() throws Exception {
        model= Mockito.mock(Model.class);
        player=Mockito.mock(Player.class);
        WindowFrame frame = new WindowFrame(WindowFrameList.AURORA_SAGRADIS);
        when(player.getWindowFrame()).thenReturn(frame);
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
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(0, 0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(0, 1));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }

        WindowFrame frame=new WindowFrame(WindowFrameList.FIRELIGHT);
        test.start(player);
        try {
            test.setParameter(frame);
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(frame.getCell(0, 0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                test.setParameter(player.getWindowFrame().getCell(0, 1));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        catch(InvalidMoveException e){
            assertEquals("Wrong parameter", e.getMessage());
        }

        test.start(player);
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(0, 0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                test.setParameter(player.getWindowFrame().getCell(0, 4));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch(InvalidMoveException e){
            assertEquals("Cell and dice colors must be equal", e.getMessage());
        }

        test.start(player);
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame().getCell(0, 0));
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            test.setParameter(player.getWindowFrame());
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            try {
                test.setParameter(player.getWindowFrame().getCell(3, 0));
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }catch(InvalidMoveException e){
            assertEquals("Invalid adjacent dices", e.getMessage());
        }
    }

    @Test
    public void setParameter() throws InvalidMoveException {
        test.start(player);
        try{
            try {
                test.setParameter(player.getWindowFrame());
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        catch (InvalidMoveException e){
            assert false;
        }
        try{
            try {
                test.setParameter(player.getWindowFrame());
            } catch (WrongParameter wrongParameter) {
                wrongParameter.printStackTrace();
            }
        }
        catch(InvalidMoveException e){
            assertEquals("Wrong parameter", e.getMessage());
        }
    }
}