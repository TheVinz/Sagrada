package common.viewchangement;

import common.Changer;

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

    public LoadPublicObjectiveCards(int[] publicObjectiveCards){this.publicObjectiveCards=publicObjectiveCards;}


    public int[] getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void change(Changer changer){
        changer.change(this);
    }
}
