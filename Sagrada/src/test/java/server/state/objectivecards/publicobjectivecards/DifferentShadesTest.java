package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.objectivecards.publicobjectivecards.DifferentShades;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentShadesTest {  //andata con vetrata vuota

    private WindowFrame windowFrame;
    private DifferentShades differentShades;
    @Before
    public void initClass(){
        windowFrame = new WindowFrame(WindowFrameList.VIA_LUX);
        differentShades = new DifferentShades();
    }
    @Test
    public void shouldCalculatePoints() throws InvalidMoveException {
        assertEquals(0,differentShades.calculatePoints(windowFrame));    //vetrata vuota
        windowFrame.getCell(0,0).put(new Dice(Color.GREEN, 1));
        windowFrame.getCell(0,1).put(new Dice(Color.GREEN, 3));
        windowFrame.getCell(0,2).put(new Dice(Color.GREEN, 2));
        windowFrame.getCell(1,0).put(new Dice(Color.GREEN, 6));
        windowFrame.getCell(1,3).put(new Dice(Color.GREEN, 5));
        windowFrame.getCell(2,1).put(new Dice(Color.GREEN, 1));
        windowFrame.getCell(3,1).put(new Dice(Color.GREEN, 4));
        windowFrame.getCell(3,4).put(new Dice(Color.GREEN, 3));
        assertEquals(5, differentShades.calculatePoints(windowFrame));  //vetrata con 6 dadi di valore diverso

        windowFrame.getCell(2,0).put(new Dice(Color.GREEN, 4));
        windowFrame.getCell(1,2).put(new Dice(Color.PURPLE, 2));
        windowFrame.getCell(1,1).put(new Dice(Color.GREEN, 6));
        windowFrame.getCell(2,3).put(new Dice(Color.BLUE, 5));
        assertEquals(10,differentShades.calculatePoints(windowFrame));   //vetrata con 12 dadi di valore diverso

    }
    @Test
    public void shouldGetNumber(){
        assertEquals(5,differentShades.getNumber());
    }


}