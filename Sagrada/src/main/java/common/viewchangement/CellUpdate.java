package common.viewchangement;

import client.view.ChangementVisitor;

public class CellUpdate implements Changement {
    public static final int WINDOW_FRAME=1;
    public static final int DRAFT_POOL=2;
    public static final int ROUND_TRACK=3;

    private int cellType;
    private int row;
    private int column;
    private int value;
    private char color;

    public CellUpdate(int type, int row, int column, int value, char color) {
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

    public char getColor() {
        return color;
    }

    public int getCellType(){
        return cellType;
    }

    @Override
    public void change(ChangementVisitor changementVisitor) {
        changementVisitor.change(this);
    }
}
