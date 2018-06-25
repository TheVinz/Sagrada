package common.viewchangement;

import common.Changer;

/**
 * The <tt>SinglePlayerEndGame</tt> class contains the data to send to the client about the final points in SinglePlayer mode.
 */
public class SinglePlayerEndGame extends Changement {
    private final int targetPoints;
    private final int[] vectorPoints;
    private final char card;

    /**
     * Creates a new <tt>SinglePlayerEndGame</tt> changement relative to the final points in SinglePlayer mode.
     * @param targetPoints an int which indicates the points to beat to win.
     * @param vectorPoints an array of int which describes how the {@link server.model.state.player.Player} made the points.
     * @param card a char which indicates the {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard}.
     */
    public SinglePlayerEndGame(int targetPoints, int[] vectorPoints, char card){
        this.card=card;
        this.targetPoints = targetPoints;
        this.vectorPoints = vectorPoints;
    }

    /**
     * Gets an int which indicates the points to beat to win.
     * @return an int which indicates the points to beat to win.
     */
    public int getTargetPoints() {
        return targetPoints;
    }

    /**
     * Gets an array of int which describes how the Player made the points.
     * @return an array of int which describes how the Player made the points.
     */
    public int[] getVectorPoints() {
        return vectorPoints;
    }

    /**
     * Gets a char which indicates the PrivateObjectiveCard.
     * @return a char which indicates the PrivateObjectiveCard.
     */
    public char getCard(){return this.card;}

    /**
     * Delegate the handling of this <tt>SinglePlayerEndGame</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>SinglePlayerEndGame</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }


}
