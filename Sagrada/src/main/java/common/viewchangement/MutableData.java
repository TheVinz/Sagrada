package common.viewchangement;

import common.Changer;

public class MutableData extends Changement {
    private final int[] draftPoolValues;
    private final char[] draftPoolColors;
    private final int[][] roundTrackValues;
    private final char[][] roundTrackColors;
    private final String[] names;
    private final int[] ids;
    private final int[] favorTokens;
    private final String[] windowFrameReps;
    private final int[][][] windowFrameValues;
    private final char[][][] windowFrameColors;
    private final int id;

    public MutableData(int[] draftPoolValues, char[] draftPoolColors, int[][] roundTrackValues, char[][] roundTrackColors, String[] names, int[] ids, int[] favorTokens, String[] windowFrameReps, int[][][] windowFrameValues, char[][][] windowFrameColors, int id){
        this.draftPoolValues = draftPoolValues;
        this.draftPoolColors = draftPoolColors;
        this.roundTrackValues = roundTrackValues;
        this.roundTrackColors = roundTrackColors;

        this.names = names;
        this.ids = ids;
        this.favorTokens = favorTokens;
        this.windowFrameReps = windowFrameReps;
        this.windowFrameValues = windowFrameValues;
        this.windowFrameColors = windowFrameColors;
        this.id=id;
    }

    public int getId(){
        return this.id;
    }

    public int[] getDraftPoolValues() {
        return draftPoolValues;
    }

    public char[] getDraftPoolColors() {
        return draftPoolColors;
    }

    public int[][] getRoundTrackValues() {
        return roundTrackValues;
    }

    public char[][] getRoundTrackColors() {
        return roundTrackColors;
    }

    public String[] getNames() {
        return names;
    }

    public int[] getIds() {
        return ids;
    }

    public int[] getFavorTokens() {
        return favorTokens;
    }

    public String[] getWindowFrameReps() {
        return windowFrameReps;
    }

    public int[][][] getWindowFrameValues() {
        return windowFrameValues;
    }

    public char[][][] getWindowFrameColors() {
        return windowFrameColors;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
