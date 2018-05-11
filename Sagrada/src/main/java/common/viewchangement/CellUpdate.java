package common.viewchangement;

import common.ChangementVisitor;
import server.state.utilities.Color;

import java.rmi.RemoteException;

public class CellUpdate implements Changement {
    public static final int WINDOW_FRAME=1;
    public static final int DRAFT_POOL=2;
    public static final int ROUND_TRACK=3;

    private int cellType;
    private int row;
    private int column;
    private int value;
    private Color color;

    public CellUpdate(String player, int type, int row, int column, int value, Color color) {
        this.row=row;
        this.column=column;
        this.value=value;
        this.color=color;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getCellType(){
        return cellType;
    }

    @Override
    public void change(ChangementVisitor changementVisitor) {
        try{
            changementVisitor.change(this);
        }catch(RemoteException e){

        }
    }
}
