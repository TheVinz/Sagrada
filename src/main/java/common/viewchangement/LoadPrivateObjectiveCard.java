package common.viewchangement;

import common.Changer;

/**
 * The <tt>LoadPrivateObjectiveCard</tt> class contains data about a {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard}
 * of a {@link server.model.state.player.Player}.
 */
public class LoadPrivateObjectiveCard extends Changement{

    private final char color;

    /**
     * Creates a new <tt>LoadPrivateObjectiveCard</tt> changement relative to a new game.
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
