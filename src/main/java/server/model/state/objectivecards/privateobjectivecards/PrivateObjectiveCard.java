package server.model.state.objectivecards.privateobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.objectivecards.ObjectiveCard;
import server.model.state.utilities.Color;

/**
 * This is an enum class that represents all the <tt>PrivateObjectiveCard</tt>. They are assigned randomly at every
 * {@link server.model.state.player.Player} at the beginning of the game.
 * There are five PrivateObjectiveCard, one for each c{@link server.model.state.utilities.Color}.
 */
public  enum PrivateObjectiveCard implements ObjectiveCard {
    RED_SHAPES(Color.RED), BLUE_SHAPES(Color.BLUE), YELLOW_SHAPES(Color.YELLOW), PURPLE_SHAPES(Color.PURPLE), GREEN_SHAPES(Color.GREEN);
    private final Color color;
    PrivateObjectiveCard(Color color){
        this.color=color;
    }

    /**
     * Gets the color of this PrivateObjectiveCard.
     * @return a char that represents the color of this PrivateObjectiveCard.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sum the value of every {@link server.model.state.dice.Dice} on the {@link server.model.state.boards.windowframe.WindowFrame}
     * of the player with the same color of this PrivateObjectiveCard.
     * @param windowFrame WindowFrame of the player.
     * @return an int that indicates the point from the PrivateObjectiveCard.
     */
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(!windowFrame.getCell(i,j).isEmpty())
                    if(windowFrame.getCell(i,j).getDice().getColor()==this.color) points+=windowFrame.getCell(i,j).getDice().getValue();
            }
        }
        return points;
    }
}
