package common.command;

import common.response.Response;

import java.io.Serializable;

public class GameCommand implements Serializable {


    private int x;
    private int y;
    private final Response type;

    public GameCommand(Response type, int row, int column){
        this.x=row;
        this.y=column;
        this.type=type;
    }

    public GameCommand(Response type, int index){
        this.x=index;
        this.type=type;
    }

    public GameCommand(Response type){
        this.type=type;
    }

    public Response getType() {
        return type;
    }

    public int getY() { return y; }

    public int getX() {return x; }
}
