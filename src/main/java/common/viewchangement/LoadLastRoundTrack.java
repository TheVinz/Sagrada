package common.viewchangement;

import common.Changer;

/**
 * The <tt>LoadLastRoundTrack</tt> class contains data to send to the client to inform him about the update of the {@link server.model.state.boards.roundtrack.RoundTrack}.
 */
public class LoadLastRoundTrack extends Changement {
    private final int round;
    private final int[] values;
    private final char[] colors;

    /**
     * Creates a new <tt>LoadLastRoundTrack</tt> changement relative to the RoundTrack.
     * @param round the round where the RoundTrack is changed.
     * @param values the values of the {@link server.model.state.dice.Dice} to put in the RoundTrack in this round.
     * @param colors the {@link server.model.state.utilities.Color}s of the {@link server.model.state.dice.Dice} to put in the RoundTrack in this round.
     */
    public LoadLastRoundTrack(int round, int[] values, char[] colors){

        this.round = round;
        this.values = values;
        this.colors = colors;
    }

    /**
     * Gets the round where the RoundTrack is changed.
     * @return the round where the RoundTrack is changed.
     */
    public int getRound() {
        return round;
    }

    /**
     * Gets the values of the Dice to put in the RoundTrack in this round.
     * @return the values of the Dice to put in the RoundTrack in this round.
     */
    public int[] getValues() {
        return values;
    }

    /**
     * Gets the colors of the Dice to put in the RoundTrack in this round.
     * @return the colors of the Dice to put in the RoundTrack in this round.
     */
    public char[] getColors() {
        return colors;
    }

    /**
     * Delegate the handling of this <tt>LoadLastRoundTrack</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>LoadLastRoundTrackt</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
