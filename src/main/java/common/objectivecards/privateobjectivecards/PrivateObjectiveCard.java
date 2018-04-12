package common.objectivecards.privateobjectivecards;

import common.boards.windowframe.WindowFrame;
import common.objectivecards.ObjectiveCard;
import common.utilities.Color;

import static common.utilities.Color.*;

public  enum PrivateObjectiveCard implements ObjectiveCard{
    RED_SHAPES(RED), BLUE_SHAPES(BLUE), YELLOW_SHAPES(YELLOW), PURPLE_SHAPES(PURPLE), GREEN_SHAPES(GREEN);
    private final Color color;
    PrivateObjectiveCard(Color color){
        this.color=color;
    }

    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(windowFrame.getCell(i,j).getDice().getColor()==this.color) points++;
            }
        }
        return points;
    }
}
