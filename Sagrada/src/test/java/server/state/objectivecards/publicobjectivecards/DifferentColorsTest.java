package server.state.objectivecards.publicobjectivecards;

import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.dice.Dice;
import server.model.state.objectivecards.publicobjectivecards.DifferentColors;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;

public class DifferentColorsTest {  //andata con vetrata vuota
    private WindowFrame windowFrame;
    private DifferentColors differentColors;
    @Before
    public void initClass(){
        windowFrame = new WindowFrame(WindowFrameList.FIRMITAS);
        differentColors = new DifferentColors();
    }
    @Test
    public void shouldCalculatePoints(){
        assertEquals(0,differentColors.calculatePoints(windowFrame));  //vetrata vuota
        try{
            windowFrame.getCell(0,0).put(new Dice(Color.RED));
            windowFrame.getCell(0,1).put(new Dice(Color.BLUE));
            windowFrame.getCell(0,2).put(new Dice(Color.GREEN));
            windowFrame.getCell(1,1).put(new Dice(Color.RED));
            windowFrame.getCell(1,4).put(new Dice(Color.PURPLE));
            windowFrame.getCell(2,2).put(new Dice(Color.PURPLE));
            windowFrame.getCell(2,3).put(new Dice(Color.YELLOW));
            windowFrame.getCell(3,3).put(new Dice(Color.GREEN));
            windowFrame.getCell(3,4).put(new Dice(Color.BLUE));

        }
        catch (InvalidMoveException e){
            e.printStackTrace();
        }
        assertEquals(4, differentColors.calculatePoints(windowFrame));   //9 dadi di cui 6 di colore diverso

        try{
            windowFrame.getCell(1,2).put(new Dice(Color.YELLOW));
            windowFrame.getCell(1,3).put(new Dice(Color.BLUE));}
            catch(InvalidMoveException e){
            e.printStackTrace();
            }

        assertEquals(8,differentColors.calculatePoints(windowFrame));  //11  dadi di cui 6+6 diversi
    }

    @Test
    public void shouldGetNumber(){
        assertEquals(2,differentColors.getNumber());
    }

}