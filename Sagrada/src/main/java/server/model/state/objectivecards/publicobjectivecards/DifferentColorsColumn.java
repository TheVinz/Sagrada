package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.utilities.Color;

@SuppressWarnings("Duplicates")
public class DifferentColorsColumn extends PublicObjectiveCard {
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        int[] colors={0,0,0,0,0};
        for(int i=0; i<WindowFrame.COLUMNS; i++){
            for(int j=0; j<WindowFrame.ROWS; j++) {
                if(!windowFrame.getCell(j,i).isEmpty()){
                    switch(windowFrame.getCell(j,i).getDice().getColor()){
                        case Color.RED:
                            colors[0]++;
                            break;
                        case Color.BLUE:
                            colors[1]++;
                            break;
                        case Color.GREEN:
                            colors[2]++;
                            break;
                        case Color.PURPLE:
                            colors[3]++;
                            break;
                        case Color.YELLOW:
                            colors[4]++;
                            break;
/*                        default:
                            break;*/
                    }
                }
                else colors[0]+=2;
            }
            if(isGood(colors)) points+=5;
            colors=new int[]{0,0,0,0,0};
        }
        return points;
    }

    @Override
    public int getNumber() {
        return DIFFERENT_COLORS_COLUMN;
    }

    private boolean isGood(int[] colors){
        for (int color:colors) {
            if(color>1) return false;
        }
        return true;
    }
}
