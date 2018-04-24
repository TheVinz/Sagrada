package server.state.objectivecards.publicobjectivecards;

import server.state.boards.windowframe.WindowFrame;

@SuppressWarnings("Duplicates")
public class DifferentColorsRow extends PublicObjectiveCard{
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        int[] colors={0,0,0,0,0};
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++) {
                if(!windowFrame.getCell(i,j).isEmpty()){
                    switch(windowFrame.getCell(i,j).getDice().getColor()){
                        case RED:
                            colors[0]++;
                            break;
                        case BLUE:
                            colors[1]++;
                            break;
                        case GREEN:
                            colors[2]++;
                            break;
                        case PURPLE:
                            colors[3]++;
                            break;
                        case YELLOW:
                            colors[4]++;
                            break;
/*                        default:
                            break;*/
                    }
                }
                else colors[0]+=2;
            }
            if(isGood(colors)) points+=6;
            colors=new int[]{0,0,0,0,0};
        }
        return points;
    }

    private boolean isGood(int[] colors){
        for (int color:colors) {
            if(color>1) return false;
        }
        return true;
    }
}
