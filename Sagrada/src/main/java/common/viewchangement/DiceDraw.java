package common.viewchangement;

import client.view.Changer;

public class DiceDraw extends Changement {
    private final int id;
    private final char color;

    public DiceDraw(int id, char color){

        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public char getColor() {
        return color;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
