package common.viewchangement;

import common.Changer;

public class LoadId extends Changement {
    private final int id;

    public LoadId(int id){

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
