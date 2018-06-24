package common.viewchangement;

import common.Changer;

public class LoadLastRoundTrack extends Changement {
    private final int round;
    private final int[] values;
    private final char[] colors;

    public LoadLastRoundTrack(int round, int[] values, char[] colors){

        this.round = round;
        this.values = values;
        this.colors = colors;
    }

    public int getRound() {
        return round;
    }

    public int[] getValues() {
        return values;
    }

    public char[] getColors() {
        return colors;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
