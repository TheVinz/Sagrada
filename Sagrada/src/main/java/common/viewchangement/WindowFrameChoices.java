package common.viewchangement;

import client.view.Changer;

public class WindowFrameChoices extends Changement {
    private final int[] favorTokens;
    private final String[] reps;

    public WindowFrameChoices(String[] reps, int[] favorTokens)
    {

        this.favorTokens = favorTokens;
        this.reps = reps;
    }

    public int[] getFavorTokens() {
        return favorTokens;
    }

    public String[] getReps() {
        return reps;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
