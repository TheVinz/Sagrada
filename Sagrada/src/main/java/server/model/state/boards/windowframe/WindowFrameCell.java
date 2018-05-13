package server.model.state.boards.windowframe;
import server.model.state.ModelObject;
import server.model.state.boards.Cell;
import server.model.state.utilities.Color;
import server.state.boards.*;

public class WindowFrameCell extends Cell implements ModelObject {

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


    @Override
    public int getType() {
        return WINDOW_FRAME_CELL;
    }
}
