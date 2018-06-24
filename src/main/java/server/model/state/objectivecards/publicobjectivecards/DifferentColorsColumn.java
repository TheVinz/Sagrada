package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;

/**
 * The <tt>DifferentColorsColumn</tt> class implements the method that add five points for each column of a
 * {@link server.model.state.boards.windowframe.WindowFrame} with different colored dices.
 * @see server.model.state.dice.Dice
 * @see WindowFrame
 */
@SuppressWarnings("Duplicates")
public class DifferentColorsColumn extends PublicObjectiveCard {
    /**
     * Save in an array the occurrence of all the colors then checks if there aren't more then one dice with the
     * same color in a column. If a column hasn't got repeated colored dice return five point for each column.
     * @param windowFrame of the player.
     * @return an int that represents the points achieve by a player.
     */
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        int[] colors={0,0,0,0,0};
        for(int i=0; i<WindowFrame.COLUMNS; i++){
            for(int j=0; j<WindowFrame.ROWS; j++) {
                if(!windowFrame.getCell(j,i).isEmpty()){
                    switch(windowFrame.getCell(j,i).getDice().getColor()){
                        case RED:
                            colors[0]++;
                            break;
                        case BLUE:
                            colors[1]++;
                            break;
                        case GREEN:
                            colors[2]++;
                            break;
                        case PURPLE:
                            colors[3]++;
                            break;
                        case YELLOW:
                            colors[4]++;
                            break;
                    }
                }
                else colors[0]+=2;
            }
            if(isGood(colors)) points+=5;
            colors=new int[]{0,0,0,0,0};
        }
        return points;
    }

    /**
     *
     * Gets the number that indicates the PublicObjectiveCard.
     * @return an int corresponding to DIFFERENT_COLORS_COLUMN.
     */
    @Override
    public int getNumber() {
        return DIFFERENT_COLORS_COLUMN;
    }

    /**
     * A boolean that controls the occurrence of a color in a column. True if the color isn't repeated, false if it is.
     * @param colors an array where are marked the occurrence of the colors.
     * @return true if a color in a column isn't repeated, false if it is.
     */
    private boolean isGood(int[] colors){
        for (int color:colors) {
            if(color>1) return false;
        }
        return true;
    }
}
