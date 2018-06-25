package common.viewchangement;

import common.Changer;

/**
 *  The <tt>WindowFrameChoices</tt> class contains the data to send to the client about the available
 *  {@link server.model.state.boards.windowframe.WindowFrame} to choose at the begin of the game.
 */
public class WindowFrameChoices extends Changement {
    private final int[] favorTokens;
    private final String[] reps;

    /**
     * Creates a new <tt>WindowFrameChoices</tt> changement relative to the available WindowFrame to choose at the begin of the game.
     * @param reps an array of string which indicates the WindowFrame from the class {@link server.model.state.boards.windowframe.WindowFrameList}.
     * @param favorTokens an array of int which indicates the FavorTokens of the available WindowFrame.
     */
    public WindowFrameChoices(String[] reps, int[] favorTokens)
    {

        this.favorTokens = favorTokens;
        this.reps = reps;
    }

    /**
     * Gets an array of int which indicates the FavorTokens of the available WindowFrame.
     * @return an array of int which indicates the FavorTokens of the available WindowFrame.
     */
    public int[] getFavorTokens() {
        return favorTokens;
    }

    /**
     * Gets an array of string which indicates the WindowFrame from the class WindowFrameList.
     * @return an array of string which indicates the WindowFrame from the class WindowFrameList.
     */
    public String[] getReps() {
        return reps;
    }

    /**
     * Delegate the handling of this <tt>WindowFrameChoices</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>WindowFrameChoices</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
