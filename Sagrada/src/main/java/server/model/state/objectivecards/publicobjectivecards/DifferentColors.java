package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;

public class DifferentColors extends PublicObjectiveCard{
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

    @Override
    public int getNumber() {
        return DIFFERENT_COLORS;
    }
}
