package common.viewchangement;

import common.Changer;
import common.response.Response;

public class RemovedDice extends Changement{
    private final int id;
    private final Response type;
    private final int index;

    public RemovedDice(int id, Response type, int index){

        this.id = id;
        this.type = type;
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public Response getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
