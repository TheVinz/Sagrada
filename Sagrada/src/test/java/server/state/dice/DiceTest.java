package server.state.dice;

import common.exceptions.InvalidMoveException;
import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DiceTest {
    private Dice dice;
    private Dice diceC;
    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private Dice dice5;
    private Dice dice6;

    @Before
    public void initClass() {
        dice = new Dice(Color.BLUE,4);
        diceC = new Dice(Color.RED);
        dice1 = new Dice(Color.BLUE,1);
        dice2 = new Dice(Color.BLUE,2);
        dice3 = new Dice(Color.BLUE,3);
        dice5 = new Dice(Color.BLUE,5);
        dice6 = new Dice(Color.BLUE,6);
    }

    @Test
    public void shouldGetColor() {
        assertEquals(Color.BLUE,dice.getColor()); }

    @Test
    public void shouldGetValue(){
        assertEquals(4,dice.getValue());}

    @Test
    public void shouldIncrease() throws InvalidMoveException {
        dice.increase();
        assertEquals(5,dice.getValue());
        try{
            dice6.increase();
            fail("should be exceptions!");}
            catch(InvalidMoveException e){
             assertEquals("Cannot increase 6",e.getMessage());}
        }

    @Test
    public void shouldDecrease() throws InvalidMoveException {
        dice.decrease();
        assertEquals(3,dice.getValue());
        try{
            dice1.decrease();
            fail("should be exceptions!");}
        catch(InvalidMoveException e){
            assertEquals("Cannot decrease 1",e.getMessage());}
    }
    @Test
    public void shouldFlip(){
        dice.flip();
        assertEquals(3,dice.getValue());
        dice1.flip();
        assertEquals(6,dice1.getValue());
        dice2.flip();
        assertEquals(5,dice2.getValue());
        dice3.flip();
        assertEquals(4,dice3.getValue());
        dice5.flip();
        assertEquals(2,dice5.getValue());
        dice6.flip();
        assertEquals(1,dice6.getValue());
    }

}
