package common.viewchangement;


import common.Changer;

/**
 * The <tt>RefilledDraftPool</tt> class contains the data to send to the client
 * of the {@link server.model.state.boards.draftpool.DraftPool} of the new turn.
 */
public class RefilledDraftPool extends Changement {
    private final int[] values;
    private final char[] colors;

    /**
     * Creates a new <tt>RefilledDraftPool</tt> changement relative to a new turn.
     * @param values an array of int which represents the values of the {@link server.model.state.dice.Dice} in the new DraftPool.
     * @param colors an array of colors which represents the colors of the Dice in the new DraftPool.
     */
    public RefilledDraftPool(int[] values, char[] colors){
        this.values=values;
        this.colors=colors;
    }


    /**
     * Gets an array of int which represents the values of the Dice in the new DraftPool.
     * @return an array of int which represents the values of the Dice in the new DraftPool.
     */
    public int[] getValues() {
        return values;
    }

    /**
     * Gets an array of colors which represents the values of the Dice in the new DraftPool.
     * @return an array of colors which represents the values of the Dice in the new DraftPool.
     */
    public char[] getColors() {
        return colors;
    }

    /**
     * Delegate the handling of this <tt>RefilledDraftPool</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>RefilledDraftPool</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
