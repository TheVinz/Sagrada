package server.state.boards.windowframe;
import server.state.boards.*;
import common.exceptions.InvalidMoveException;
import server.state.utilities.Color;
import server.state.dice.Dice;

public class WindowFrameCell implements Cell {
    //Dado presente nella casella
    private Dice dice;
    private Color color;
    private  final int row;
    private  final int columnn;
    private int shade;

    WindowFrameCell(int row, int col){
        this.color=null;
        this.shade=0;
        this.row=row;
        this.columnn=col;
    }

    WindowFrameCell(Color color, int row, int columnn){
        this.color=color;
        this.shade=0;
        this.row=row;
        this.columnn=columnn;
    }

    WindowFrameCell(int shade, int row, int columnn){
        this.color=null;
        this.shade=shade;
        this.row=row;
        this.columnn=columnn;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumnn() {
        return columnn;
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

    @Override
    public Dice removeDice() {
        Dice result=this.dice;
        this.dice=null;
        return result;
    }
}
