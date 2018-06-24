package common.viewchangement;

import common.Changer;

import java.io.Serializable;

public class PrivateObjectiveCardsChoice extends Changement {

    private final char card1;
    private final char card2;

    public PrivateObjectiveCardsChoice(char card1, char card2){

        this.card1 = card1;
        this.card2 = card2;
    }
    public void change(Changer changer){
        changer.change(this);
    }

    public char getCard1() {
        return card1;
    }

    public char getCard2() {
        return card2;
    }
}
