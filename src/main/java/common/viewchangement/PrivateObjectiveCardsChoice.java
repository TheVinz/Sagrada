package common.viewchangement;

import common.Changer;

import java.io.Serializable;

/**
 * The <tt>PrivateObjectiveCardChoice</tt> class contains data to send to the client about the choice of the player about which
 * {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard} he choose at the end of the game
 * for calculate the final points.
 */
public class PrivateObjectiveCardsChoice extends Changement {

    private final char card1;
    private final char card2;

    /**
     * Creates a new <tt>PrivateObjectiveCardsChoice</tt> changement relative to a new game.
     * @param card1 the first PrivateObjectiveCard.
     * @param card2 the second PrivateObjectiveCard.
     */
    public PrivateObjectiveCardsChoice(char card1, char card2){

        this.card1 = card1;
        this.card2 = card2;
    }
    /**
     * Gets te first PrivateObjectiveCard.
     * @return the first PrivateObjectiveCard.
     */
    public char getCard1() {
        return card1;
    }

    /**
     * Gets te second PrivateObjectiveCard.
     * @return the second PrivateObjectiveCard.
     */
    public char getCard2() {
        return card2;
    }

    /**
     * Delegate the handling of this <tt>PrivateObjectiveCardsChoice</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>PrivateObjectiveCardsChoice</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }


}
