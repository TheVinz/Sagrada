package server.state.objectivecards.privateobjectivecards;

import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.ObjectiveCard;
import server.state.utilities.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static server.state.utilities.Color.*;

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
                if(!windowFrame.getCell(i,j).isEmpty())
                    if(windowFrame.getCell(i,j).getDice().getColor()==this.color) points+=windowFrame.getCell(i,j).getDice().getValue();
            }
        }
        return points;
    }
}
