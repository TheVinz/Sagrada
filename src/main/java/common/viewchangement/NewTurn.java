package common.viewchangement;

import common.Changer;

public class NewTurn extends Changement {
    private final int id;
    public NewTurn(int id){

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
