package common.viewchangement;

import client.view.Changer;

public class EndGame extends Changement {
    private final char[] charCards;
    private final int[] scoreboardIds;
    private final int[][] matrixPoins;

    public EndGame(char[] charCards, int[] scoreboardIds, int[][] matrixPoins){

        this.charCards = charCards;
        this.scoreboardIds = scoreboardIds;
        this.matrixPoins = matrixPoins;
    }

    public char[] getCharCards() {
        return charCards;
    }

    public int[] getScoreboardIds() {
        return scoreboardIds;
    }

    public int[][] getMatrixPoins() {
        return matrixPoins;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
