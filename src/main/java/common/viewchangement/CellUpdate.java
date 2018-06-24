package common.viewchangement;


import common.Changer;
import common.response.Response;

public class CellUpdate extends Changement {


    private final int playerId;
    private final Response cellType;
    private final int row;
    private final int column;
    private final int value;
    private final char color;

    public CellUpdate(int player, Response type, int row, int column, int value, char color) {
        this.playerId = player;
        this.cellType=type;
        this.row=row;
        this.column=column;
        this.value=value;
        this.color=color;
    }

    public CellUpdate(int player, Response type, int row, int value, char color) {
        this.playerId = player;
        this.cellType=type;
        this.row=row;
        this.column=-1;
        this.value=value;
        this.color=color;
    }


    public int getPlayerId() {
        return playerId;
    }

    public Response getCellType() {
        return cellType;
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

    public void change(Changer changer){
        changer.change(this);
    }

}
