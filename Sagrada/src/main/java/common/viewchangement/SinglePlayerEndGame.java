package common.viewchangement;

import common.Changer;

public class SinglePlayerEndGame extends Changement {
    private final int targetPoints;
    private final int[] vectorPoints;
    private final char card;

    public SinglePlayerEndGame(int targetPoints, int[] vectorPoints, char card){
        this.card=card;
        this.targetPoints = targetPoints;
        this.vectorPoints = vectorPoints;
    }


    public void change(Changer changer){
        changer.change(this);
    }

    public int getTargetPoints() {
        return targetPoints;
    }

    public int[] getVectorPoints() {
        return vectorPoints;
    }

    public char getCard(){return this.card;}
}
