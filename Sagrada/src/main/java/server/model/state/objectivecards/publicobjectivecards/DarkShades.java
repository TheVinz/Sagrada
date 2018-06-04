package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;

/**
 * The DarkShades class implements the method that add two points for each set of {@link server.model.state.dice.Dice}
 * with value five and six on the {@link server.model.state.boards.windowframe.WindowFrame}.
 */
public class DarkShades extends PublicObjectiveCard {
    /**
     * Calculates the number of Dice with value six and five. Returns the lower one multiplied by two.
     * @param windowFrame of the player.
     * @return an int that indicates the points.
     */
    public int calculatePoints(WindowFrame windowFrame) {
        int[] shades= {0,0};
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(!windowFrame.getCell(i,j).isEmpty()){
                    switch(windowFrame.getCell(i,j).getDice().getValue()){
                        case 5:
                            shades[0]++;
                            break;
                        case 6:
                            shades[1]++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        if(shades[0]<shades[1]) return 2*shades[0];
        else return shades[1]*2;
    }

    /**
     * Gets the number that indicates the PublicObjectiveCard.
     * @return an int corresponding to CARD_SHADES
     */
    @Override
    public int getNumber() {
        return DARK_SHADES;
    }
}
