package common.viewchangement;

import common.Changer;

public class SinglePlayerEndGame extends Changement {
    private final int finalScore;
    private final int[] vectorPoints;

    public SinglePlayerEndGame(int finalScore, int[] vectorPoints){
        this.finalScore = finalScore;
        this.vectorPoints = vectorPoints;
    }


    public void change(Changer changer){
        changer.change(this);
    }

    public int getFinalScore() {
        return finalScore;
    }

    public int[] getVectorPoints() {
        return vectorPoints;
    }
}
