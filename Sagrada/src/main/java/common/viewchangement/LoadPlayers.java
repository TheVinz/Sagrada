package common.viewchangement;

import common.Changer;

public class LoadPlayers extends Changement {
    private final String[] names;
    private final int[] ids;
    private final String[] windowFrameReps;
    private final int[] windowFrameFavorTokens;
    public LoadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens){
        this.names = names;

        this.ids = ids;
        this.windowFrameReps = windowFrameReps;
        this.windowFrameFavorTokens = windowFrameFavorTokens;
    }

    public String[] getNames() {
        return names;
    }

    public int[] getIds() {
        return ids;
    }

    public String[] getWindowFrameReps() {
        return windowFrameReps;
    }

    public int[] getWindowFrameFavorTokens() {
        return windowFrameFavorTokens;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
