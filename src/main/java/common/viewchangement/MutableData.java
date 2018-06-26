package common.viewchangement;

import common.Changer;

/**
 * The <tt>MutableData</tt> class contains the data to send to the client whose going to be reinserted to the game.
 */
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

    /**
     * Creates a new <tt>MutableData</tt> changement relative to a {@link server.model.state.player.Player} whose going to be reinserted to the game.
     * @param draftPoolValues an array of int which indicates the values of the {@link server.model.state.dice.Dice} in the {@link server.model.state.boards.draftpool.DraftPool}.
     * @param draftPoolColors an array of char which indicates the colors of the Dice in the DraftPool.
     * @param roundTrackValues an array of int which indicates the values of the Dice in the {@link server.model.state.boards.roundtrack.RoundTrack}.
     * @param roundTrackColors an array of char which indicates the colors of the Dice in the RoundTrack.
     * @param names an array of string which indicates the names of the players in the game.
     * @param ids an array of int which indicates the ids of the players in the game.
     * @param favorTokens an array of int which indicates the FavorTokens of the players in the game.
     * @param windowFrameReps an array of string which indicates the initial {@link server.model.state.boards.windowframe.WindowFrame} of the players in the game.
     * @param windowFrameValues an array of matrix which indicates the values in every {@link server.model.state.boards.windowframe.WindowFrameCell} of the players in the game.
     * @param windowFrameColors an array of matrix which indicates the colors in every WindowFrameCell of the player in the game.
     * @param id an int which indicates the new id of the player reinserted in the game.
     */
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

    /**
     * Gets an int which indicates the new id of the player reinserted in the game.
     * @return an int which indicates the new id of the player reinserted in the game.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Gets an array of int which indicates the values of the Dice in the DraftPool.
     * @return an array of int which indicates the values of the Dice in the DraftPool.
     */
    public int[] getDraftPoolValues() {
        return draftPoolValues;
    }

    /**
     * Gets an array of char which indicates the colors of the Dice in the DraftPool.
     * @return an array of char which indicates the colors of the Dice in the DraftPool.
     */
    public char[] getDraftPoolColors() {
        return draftPoolColors;
    }

    /**
     * Gets an array of int which indicates the values of the Dice in the RoundTrack.
     * @return an array of int which indicates the values of the Dice in the RoundTrack.
     */
    public int[][] getRoundTrackValues() {
        return roundTrackValues;
    }

    /**
     * Gets an array of char which indicates the colors of the Dice in the RoundTrack.
     * @return an array of char which indicates the colors of the Dice in the RoundTrack.
     */
    public char[][] getRoundTrackColors() {
        return roundTrackColors;
    }

    /**
     * Gets an array of string which indicates the names of the players in the game.
     * @return an array of string which indicates the names of the players in the game.
     */
    public String[] getNames() {
        return names;
    }

    /**
     * Gets an array of int which indicates the ids of the players in the game.
     * @return an array of int which indicates the ids of the players in the game.
     */
    public int[] getIds() {
        return ids;
    }

    /**
     * Gets an array of int which indicates the FavorTokens of the players in the game.
     * @return an array of int which indicates the FavorTokens of the players in the game.
     */
    public int[] getFavorTokens() {
        return favorTokens;
    }

    /**
     * Gets an array of string which indicates the initial WindowFrame of the players in the game.
     * @return an array of string which indicates the initial WindowFrame of the players in the game.
     */
    public String[] getWindowFrameReps() {
        return windowFrameReps;
    }

    /**
     * Gets an array of matrix which indicates the values in every WindowFrameCell of the players in the game.
     * @return an array of matrix which indicates the values in every WindowFrameCell of the players in the game.
     */
    public int[][][] getWindowFrameValues() {
        return windowFrameValues;
    }

    /**
     * Gets an array of matrix which indicates the colors in every WindowFrameCell of the player in the game.
     * @return an array of matrix which indicates the colors in every WindowFrameCell of the player in the game.
     */
    public char[][][] getWindowFrameColors() {
        return windowFrameColors;
    }

    /**
     * Delegate the handling of this <tt>MutableData</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>MutableData</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
