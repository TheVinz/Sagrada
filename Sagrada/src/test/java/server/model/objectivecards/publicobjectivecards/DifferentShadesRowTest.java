package server.model.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.objectivecards.publicobjectivecards.DifferentShadesRow;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentShadesRowTest {
    private WindowFrame windowFrame;
    private DifferentShadesRow differentShadesRow;
    @Before
    public void initClass(){
        windowFrame = new WindowFrame(WindowFrameList.AURORAE_MAGNIFICUS);
       differentShadesRow = new DifferentShadesRow();
    }
    @Test
    public void shouldCalculatePoints() throws InvalidMoveException {
        assertEquals(0,differentShadesRow.calculatePoints(windowFrame));    //vetrata vuota
        windowFrame.getCell(0,1).put(new Dice(Color.GREEN, 1));
        windowFrame.getCell(0,2).put(new Dice(Color.GREEN, 2));
        windowFrame.getCell(0,0).put(new Dice(Color.GREEN, 3));
        windowFrame.getCell(0,3).put(new Dice(Color.GREEN, 4));
        windowFrame.getCell(0,4).put(new Dice(Color.GREEN, 5));
        windowFrame.getCell(2,0).put(new Dice(Color.GREEN, 6));
        assertEquals(5, differentShadesRow.calculatePoints(windowFrame));   //vetrata con una riga piena e una no
        windowFrame.getCell(1,1).put(new Dice(Color.GREEN, 1));
        windowFrame.getCell(1,2).put(new Dice(Color.GREEN, 2));
        windowFrame.getCell(1,0).put(new Dice(Color.GREEN, 3));
        windowFrame.getCell(1,3).put(new Dice(Color.GREEN, 4));
        windowFrame.getCell(1,4).put(new Dice(Color.GREEN, 1));
        assertEquals(5,differentShadesRow.calculatePoints(windowFrame));   //vetrata con una riga piena buona, una piena non buona
    }
    @Test
    public void shouldGetNumber(){
        assertEquals(7,differentShadesRow.getNumber());
    }

}