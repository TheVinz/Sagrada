package server.state.boards.windowframe;
import server.state.boards.*;
import common.exceptions.InvalidMoveException;
import server.state.utilities.Color;
import server.state.dice.Dice;

public class WindowFrameCell extends Cell {

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



}
