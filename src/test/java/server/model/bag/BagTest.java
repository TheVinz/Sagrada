package server.model.bag;

import org.junit.Before;
import org.junit.Test;
import server.model.state.bag.Bag;
import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BagTest {
    private Bag bag;
    private Dice dice;


    @Before
    public void setUp(){
        bag=new Bag();
        dice = new Dice(Color.BLUE,4);
    }

    @Test
    public void draw() {
        ArrayList<Color> colors=new ArrayList<>(90);
        for(int i=0; i<90; i++){
            colors.add(bag.draw().getColor());
        }
        int[] rgbpy= {0,0,0,0,0};
        for(Color color:colors){
            switch (color){
                case RED:
                    rgbpy[0]++;
                    break;
                case GREEN:
                    rgbpy[1]++;
                    break;
                case BLUE:
                    rgbpy[2]++;
                    break;
                case PURPLE:
                    rgbpy[3]++;
                    break;
                case YELLOW:
                    rgbpy[4]++;
                    break;
            }
        }
        assertEquals(18,rgbpy[0]);
        assertEquals(18,rgbpy[1]);
        assertEquals(18,rgbpy[2]);
        assertEquals(18,rgbpy[3]);
        assertEquals(18,rgbpy[4]);
    }

    @Test
    public void shouldInsert() {

        bag.insert(dice);
        assertNotEquals(null,bag);
    }
    @Test
    public void shouldDraw(){
        assertEquals(4,bag.draw(4).getValue());
        assertNotEquals(null,bag.draw(4));

    }
}