package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.boards.windowframe.WindowFrame;
import server.state.dice.Dice;
import server.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentShadesTest {  //andata con vetrata vuota

    private WindowFrame windowFrame;
    private DifferentShades differentShades;
    @Before
    public void initClass(){
        windowFrame = WindowFrame.AURORA_SAGRADIS;
        differentShades = new DifferentShades();
    }
    @Test
    public void shouldCalculatePoints(){


        assertEquals(0,differentShades.calculatePoints(windowFrame));
    }

}