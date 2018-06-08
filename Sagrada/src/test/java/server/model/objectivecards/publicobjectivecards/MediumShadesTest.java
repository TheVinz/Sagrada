package server.model.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.objectivecards.publicobjectivecards.MediumShades;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class MediumShadesTest {   //copertoo
    private WindowFrame windowFrame;
    private MediumShades mediumShades;
    @Before
    public void initClass(){
        windowFrame =new WindowFrame(WindowFrameList.VIA_LUX);
        mediumShades = new MediumShades();
    }
    @Test
    public void shouldCalculatePoints(){
        try {
            windowFrame.getCell(3,2).put(new Dice(Color.BLUE, 3));
            windowFrame.getCell(1,3).put(new Dice(Color.BLUE, 4));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        assertEquals(2,mediumShades.calculatePoints(windowFrame));
    }

    @Test
    public void shouldGetNumber(){
        assertEquals(8,mediumShades.getNumber());
    }


}