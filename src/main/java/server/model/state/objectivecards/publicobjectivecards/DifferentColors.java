package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;

/**
 * The <tt>DifferentColors</tt> class implements the method that add four point for every set of different
 * colored {@link server.model.state.dice.Dice } in the {@link server.model.state.boards.windowframe.WindowFrame}.
 *  * @see server.model.state.dice.Dice
 *  * @see WindowFrame
 */
public class DifferentColors extends PublicObjectiveCard{
    /**
     * Calculates the occurrence of every color and saved them in an array. Returns the lower value multiplied by four.
     * @param windowFrame of the player.
     * @return an int that indicates the points.
     */
    @Override
    public int calculatePoints(WindowFrame windowFrame) {
        int[] colors= {0,0,0,0,0};
        int min;
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(!windowFrame.getCell(i,j).isEmpty()){
                    switch(windowFrame.getCell(i,j).getDice().getColor()){
                        case BLUE:
                            colors[0]++;
                            break;
                        case RED:
                            colors[1]++;
                            break;
                        case GREEN:
                            colors[2]++;
                            break;
                        case YELLOW:
                            colors[3]++;
                            break;
                        case PURPLE:
                            colors[4]++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        min=colors[0];
        for(int i=1; i<colors.length; i++){
            if(colors[i]<min) min=colors[i];
        }
        return min*4;
    }

    /**
     * Gets the number that indicates the PublicObjectiveCard.
     * @return an int corresponding to DIFFERENT_COLORS.
     */
    @Override
    public int getNumber() {
        return DIFFERENT_COLORS;
    }
}
