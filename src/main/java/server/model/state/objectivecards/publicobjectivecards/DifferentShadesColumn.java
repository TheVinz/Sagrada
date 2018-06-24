package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;

/**
 * The <tt>DifferentShadesColumn</tt> class implements the method that add four points for each column of a
 * {@link server.model.state.boards.windowframe.WindowFrame} with different shaded dices.
 * @see WindowFrame
 * @see server.model.state.dice.Dice
 */
@SuppressWarnings("Duplicates")
public class DifferentShadesColumn extends PublicObjectiveCard {
    /**
     * Save in an array the occurrence of all the shades then checks if there aren't more then one dice with the
     * same shade in a column. If a column hasn't got repeated valued dice return five point for each column.
     * @param windowFrame of the player.
     * @return an int that represents the points achieve by a player.
     */
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        int[] shades={0,0,0,0,0,0};
        for(int i=0; i<WindowFrame.COLUMNS; i++){
            for(int j=0; j<WindowFrame.ROWS; j++) {
                if(!windowFrame.getCell(j,i).isEmpty()){
                    switch(windowFrame.getCell(j,i).getDice().getValue()){
                        case 1:
                            shades[0]++;
                            break;
                        case 2:
                            shades[1]++;
                            break;
                        case 3:
                            shades[2]++;
                            break;
                        case 4:
                            shades[3]++;
                            break;
                        case 5:
                            shades[4]++;
                            break;
                        case 6:
                            shades[5]++;
                            break;
/*                        default:
                            break;*/
                    }
                }
                else shades[0]+=2;
            }
            if(isGood(shades)) points+=4;
            shades=new int[]{0,0,0,0,0,0};
        }
        return points;
    }

    /**
     * Gets the number that indicates the PublicObjectiveCard.
     * @return an int corresponding to DIFFERENT_SHADES_COLUMN.
     */
    @Override
    public int getNumber() {
        return DIFFERENT_SHADES_COLUMN;
    }

    /**
     * A boolean that controls the occurrence of a shade in a column. True if the shade isn't repeated, false if it is.
     * @param colors an array where are marked the occurrence of the shades.
     * @return true if a shade in a column isn't repeated, false if it is.
     */
    private boolean isGood(int[] colors){
        for (int color:colors) {
            if(color>1) return false;
        }
        return true;
    }
}
