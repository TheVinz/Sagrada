package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class ColoredDiagonalTest {     //nullpointerexception
    private WindowFrame windowFrame;
    private ColoredDiagonal coloredDiagonal;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.AURORA_SAGRADIS;
        coloredDiagonal = new ColoredDiagonal();
    }
    @Test
    public void shouldCalculatePoints() throws InvalidMoveException {
        assertEquals(0,coloredDiagonal.calculatePoints(windowFrame));   //vetrata vuota
        windowFrame.getCell(0,0).put(new Dice(Color.GREEN));
        windowFrame.getCell(1,1).put(new Dice(Color.GREEN));
        windowFrame.getCell(2,2).put(new Dice(Color.GREEN));
        windowFrame.getCell(1,3).put(new Dice(Color.GREEN));
        windowFrame.getCell(2,4).put(new Dice(Color.GREEN));
        windowFrame.getCell(3,0).put(new Dice(Color.BLUE));
        windowFrame.getCell(2,1).put(new Dice(Color.BLUE));
        windowFrame.getCell(0,4).put(new Dice(Color.RED));
        assertEquals(7, coloredDiagonal.calculatePoints(windowFrame));   //due diagonali di colori diversi
    }


}