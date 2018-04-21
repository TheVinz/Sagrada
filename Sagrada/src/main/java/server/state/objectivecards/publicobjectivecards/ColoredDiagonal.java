package server.state.objectivecards.publicobjectivecards;

import server.state.boards.windowframe.WindowFrame;
import server.state.utilities.Color;

public class ColoredDiagonal extends PublicObjectiveCard{
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(isValid(i,j,windowFrame)) points++;
            }
        }
        return points;
    }

    private boolean isValid(int i, int j, WindowFrame windowFrame){
        Color color=windowFrame.getCell(i,j).getDice().getColor();
        return isValidRow(i+1) && isValidColumn(j+1) && windowFrame.getCell(i+1,j+1).getDice().getColor()==color ||
                isValidRow(i+1) && isValidColumn(j-1) && windowFrame.getCell(i+1,j-1).getDice().getColor()==color ||
                isValidRow(i-1) && isValidColumn(j+1) && windowFrame.getCell(i-1,j+1).getDice().getColor()==color ||
                isValidRow(i-1) && isValidColumn(j-1) && windowFrame.getCell(i-1,j-1).getDice().getColor()==color;
    }

    private boolean isValidRow(int i){
        return i>=0 && i<WindowFrame.ROWS;
    }

    private boolean isValidColumn(int j){
        return j>=0 && j<=WindowFrame.COLUMNS;
    }
}
