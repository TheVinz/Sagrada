package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;

/**
 * The <tt>DifferentShades</tt> class implements the method that add five points for each set of
 * six different colored dices in a {@link server.model.state.boards.windowframe.WindowFrame}.
 * @see WindowFrame
 * @see server.model.state.dice.Dice
 */
public class DifferentShades extends PublicObjectiveCard {
    /**
     * Calculates the occurrence of every shades and saved them in an array. Returns the lower value multiplied by four.
     * @param windowFrame of the player.
     * @return an int that indicates the points.
     */
    public int calculatePoints(WindowFrame windowFrame) {
        int[] shades= {0,0,0,0,0,0};
        int min;
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(!windowFrame.getCell(i,j).isEmpty()){
                    switch(windowFrame.getCell(i,j).getDice().getValue()){
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
                        default:
                            break;
                    }
                }
            }
        }
        min=shades[0];
        for(int i=1; i<shades.length; i++){
            if(shades[i]<min) min=shades[i];
        }
        return min*5;
    }

    /**
     * Gets the number that indicates the PublicObjectiveCard.
     * @return an int corresponding to DIFFERENT_SHADES.
     */
    @Override
    public int getNumber() {
        return DIFFERENT_SHADES;
    }
}
