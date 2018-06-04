package common.viewchangement;

import common.Changer;

import java.io.Serializable;

public class PrivateObjectiveCardsChoice extends Changement {
    public void change(Changer changer){
        changer.change(this);
    }
}
