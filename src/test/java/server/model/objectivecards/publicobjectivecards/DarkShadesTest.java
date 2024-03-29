package server.model.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.objectivecards.publicobjectivecards.DarkShades;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class DarkShadesTest {    //copertoo
    private WindowFrame windowFrame;
    private DarkShades darkShades;
    @Before
    public void initClass(){
        windowFrame = new WindowFrame(WindowFrameList.BATLLO);
        darkShades = new DarkShades();
    }
    @Test
    public void shouldCalculatePoints(){
        assertEquals(0,darkShades.calculatePoints(windowFrame));    //vetrata vuota
        try {
            windowFrame.getCell(3,2).put(new Dice(Color.BLUE, 5));
            windowFrame.getCell(1,3).put(new Dice(Color.BLUE, 6));
            windowFrame.getCell(1,2).put(new Dice(Color.BLUE, 5));
            windowFrame.getCell(2,3).put(new Dice(Color.BLUE, 6));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertEquals(4,darkShades.calculatePoints(windowFrame));   //vetrata con 4 dadi, due 5 e due 6

        try {
            windowFrame.getCell(2, 2).put(new Dice(Color.BLUE, 5));
        } catch (InvalidMoveException e){
            e.printStackTrace();
        }
        assertEquals(4,darkShades.calculatePoints(windowFrame));   //vetrata con 5 dadi, tre 5 e due 6
    }

    @Test
    public void shouldGetNumber(){
        assertEquals(1,darkShades.getNumber());
    }

}