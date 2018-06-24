package common.viewchangement;

import common.Changer;

public class LoadPrivateObjectiveCard extends Changement{

    private final char color;
    public LoadPrivateObjectiveCard(char color){

        this.color = color;
    }

    public char getColor() {
        return color;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
