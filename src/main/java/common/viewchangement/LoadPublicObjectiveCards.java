package common.viewchangement;

import common.Changer;

/**
 * The <tt>LoadPublicObjectiveCards</tt> class contains data about the {@link server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard} in a game
 * to send to the client.
 */
public class LoadPublicObjectiveCards extends Changement{
    public static final int COLORED_DIAGONAL = 0;
    public static final int DARK_SHADES = 1;
    public static final int DIFFERENT_COLORS = 2;
    public static final int DIFFERENT_COLORS_COLUMN = 3;
    public static final int DIFFERENT_COLORS_ROW = 4;
    public static final int DIFFERENT_SHADES = 5;
    public static final int DIFFERENT_SHADES_COLUMN = 6;
    public static final int DIFFERENT_SHADES_ROW = 7;
    public static final int MEDIUM_SHADES = 8;
    public static final int PALE_SHADES = 9;

    private final int[] publicObjectiveCards;

    /**
     * Creates a new <tt>LoadPublicObjectiveCards</tt> changement relative to the PublicObjectiveCards in a game.
     * @param publicObjectiveCards an array of int which represents the PublicObjectiveCards.
     */
    public LoadPublicObjectiveCards(int[] publicObjectiveCards){this.publicObjectiveCards=publicObjectiveCards;}


    /**
     * Gets the PublicObjectiveCards of a game.
     * @return an array of int which represents the PublicObjectiveCards.
     */
    public int[] getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    /**
     * Delegate the handling of this <tt>LoadPublicObjectiveCards</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>LoadPublicObjectiveCards</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
