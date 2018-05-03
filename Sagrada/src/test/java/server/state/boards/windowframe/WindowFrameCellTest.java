package server.state.boards.windowframe;

import static org.junit.Assert.*;
import common.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;
import server.state.dice.Dice;
import server.state.utilities.Color;

public class WindowFrameCellTest {
    private WindowFrameCell windowFrameCell;
    private WindowFrameCell windowFrameCell1;
    private WindowFrameCell windowFrameCell2;
    @Before
    public void init(){
        windowFrameCell = new WindowFrameCell(3,2);
        windowFrameCell1 = new WindowFrameCell(Color.GREEN,2,3);
        windowFrameCell2 = new WindowFrameCell(3,1,2);
    }
    @Test
    public void shouldGetRow(){
        assertEquals(2,windowFrameCell1.getRow());
        }
    @Test
    public void shouldGetColumn(){ assertEquals(2,windowFrameCell.getColumnn()); }
    @Test
    public void shouldGetShade(){
        assertEquals(3,windowFrameCell2.getShade());
    }
    @Test
    public void shouldGetColor(){
        assertEquals(Color.GREEN,windowFrameCell1.getColor());
    }

}