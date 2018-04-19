package TestGUI.common.viewchangement;

import TestGUI.server.model.boards.windowframe.WindowFrame;

public class CellUpdate implements Changement {
    public static final int WINDOW_FRAME_CELL=1;
    public static final int DRAFT_POOL_CELL=2;

    private int cellType;
    private int row;
    private int column;
    private String image;
    public CellUpdate(int row, int column, String image) {
        this.row=row;
        this.column=column;
        this.image=image;
        cellType=WINDOW_FRAME_CELL;
    }

    public CellUpdate(int index, String image){
        this.row=index;
        this.image=image;
        cellType=DRAFT_POOL_CELL;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getImage() {
        return image;
    }

    public int getCellType(){
        return cellType;
    }

    @Override
    public int getType() {
        return ChangementTypes.CELL_UPDATE;
    }
}
