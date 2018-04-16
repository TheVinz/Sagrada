package common.boards.windowframe;
import common.boards.*;
import common.exceptions.InvalidMoveException;
import common.utilities.Color;
import common.dice.Dice;

public class WindowFrameCell implements Cell {
    //Dado presente nella casella
    private Dice dice;
    private Color color;
    private int shade;

    WindowFrameCell(){
        this.color=null;
        this.shade=0;
    }

    WindowFrameCell(Color color){
        this.color=color;
        this.shade=0;
    }

    WindowFrameCell(int shade){
        this.color=null;
        this.shade=shade;
    }

    public int getShade() {
        return shade;
    }

    public Color getColor() {
        return color;
    }

    public Dice getDice(){
        return this.dice;
    }

    public void move(Cell target) throws InvalidMoveException{
        if(this.isEmpty()) throw new InvalidMoveException("Empty cell");
        else{
            target.put(this.getDice());
            dice=null;
        }
    }

    public void put(Dice dice) throws InvalidMoveException{
        if(!this.isEmpty()) throw new InvalidMoveException("Already filled cell");
        else this.dice=dice;
    }

    public boolean isEmpty(){
        return dice==null;
    }
}
