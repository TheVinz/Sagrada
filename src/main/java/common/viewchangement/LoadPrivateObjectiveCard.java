package common.viewchangement;

import common.Changer;

/**
 * The <tt>LoadPrivateObjectiveCard</tt> class contains data to send to the client about a {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard}.
 */
public class LoadPrivateObjectiveCard extends Changement{

    private final char color;

    /**
     * Creates a new <tt>LoadPrivateObjectiveCard</tt> changement relative to a PrivateObjectiveCard.
     * @param color of the PrivateObjectiveCard.
     */
    public LoadPrivateObjectiveCard(char color){

        this.color = color;
    }

    /**
     * Gets the color of the PrivateObjectiveCard.
     * @return the color of the PrivateObjectiveCard.
     */
    public char getColor() {
        return color;
    }

    /**
     * Delegate the handling of this <tt>LoadPrivateObjectiveCard</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>LoadPrivateObjectiveCard</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
