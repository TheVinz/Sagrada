package server.model.state.objectivecards.privateobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.objectivecards.ObjectiveCard;
import server.model.state.utilities.Color;

public  enum PrivateObjectiveCard implements ObjectiveCard {
    RED_SHAPES(Color.RED), BLUE_SHAPES(Color.BLUE), YELLOW_SHAPES(Color.YELLOW), PURPLE_SHAPES(Color.PURPLE), GREEN_SHAPES(Color.GREEN);
    private final Color color;
    PrivateObjectiveCard(Color color){
        this.color=color;
    }

    public Color getColor() {
        return color;
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
