package server.state.toolcards2;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import org.junit.Before;
import org.junit.Test;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.toolcards.Lathekin;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class LathekinTest {

    private Player player;
    private ToolCard toolCard;
    private boolean firstMoveDone;
    private List<ModelObject> parameters;
    private WindowFrame frame;
    private WindowFrameCell[] windowFrameCell = new WindowFrameCell[4];


    @Before
    public void setUp() throws Exception {

        Model model = spy(new Model());
        toolCard = new Lathekin(model);
        frame = new WindowFrame(WindowFrameList.AURORA_SAGRADIS);
        player = mock(Player.class);
        when(player.getWindowFrame()).thenReturn(frame);
        windowFrameCell[0]=frame.getCell(0,0);
        windowFrameCell[0].put(new Dice(Color.RED,4));
       // windowFrameCell[2]=frame.getCell(0,2);
      //  windowFrameCell[2].put(new Dice(Color.BLUE,2));
        windowFrameCell[1]=frame.getCell(0,1);
      //  windowFrameCell[1].put(new Dice(Color.RED,5));
        parameters=new ArrayList<>(8);
    }

       public void doAbility() throws InvalidMoveException {
        toolCard.start(player);
        Dice dice1 = new Dice(Color.RED, 5);
        Dice dice2 = new Dice(Color.PURPLE, 2);
       // player.getWindowFrame().getCell(0, 0).put(dice);
        player.getWindowFrame().getCell(1, 0).put(dice1);
        player.getWindowFrame().getCell(2, 2).put(dice2);
                toolCard.start(player);
    }
    @Test
    public void shouldSetParameter() throws InvalidMoveException {
        toolCard.start(player);
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrameCell[0]);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrameCell[1]);    //lancia eccezione perchè non è una windowframe
        } catch (WrongParameter wrongParameter) {
            assertEquals("Wrong parameter",wrongParameter.getMessage());
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        windowFrameCell[2]=frame.getCell(0,2);
        windowFrameCell[2].put(new Dice(Color.BLUE,3));
       try{ try {
            toolCard.setParameter(windowFrameCell[1]);     //stato iniziale, un solo dado, voglio spostarlo, non me lo fa spostare
        } catch (WrongParameter wrongParameter) {          //se ho due dadi nello stato iniziale me lo fa spostare
             wrongParameter.printStackTrace();
        }}
        catch(InvalidMoveException invalidMoveException){    //lancia la invalid move exception
            invalidMoveException.printStackTrace();
        }

        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(windowFrameCell[2]);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();
        }
        try {
            toolCard.setParameter(frame);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (WrongParameter wrongParameter) {
            wrongParameter.printStackTrace();}
            windowFrameCell[3]=frame.getCell(1,2);
            try{ try {
                toolCard.setParameter(windowFrameCell[3]);     //stato iniziale, un solo dado, voglio spostarlo, non me lo fa spostare
            } catch (WrongParameter wrongParameter) {          //se ho due dadi nello stato iniziale me lo fa spostare
                wrongParameter.printStackTrace();
            }}
            catch(InvalidMoveException invalidMoveException){    //lancia la invalid move exception
                invalidMoveException.printStackTrace();
            }
            System.out.println(""+frame.getCell(1,2).getDice().getColor());

}
}