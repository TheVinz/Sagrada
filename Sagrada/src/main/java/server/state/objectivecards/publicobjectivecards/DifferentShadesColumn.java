package server.state.objectivecards.publicobjectivecards;

import server.state.boards.windowframe.WindowFrame;

@SuppressWarnings("Duplicates")
public class DifferentShadesColumn extends PublicObjectiveCard {
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        int[] shades={0,0,0,0,0,0};
        for(int i=0; i<WindowFrame.COLUMNS; i++){
            for(int j=0; j<WindowFrame.ROWS; j++) {
                if(!windowFrame.getCell(j,i).isEmpty()){
                    switch(windowFrame.getCell(j,i).getDice().getValue()){
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
/*                        default:
                            break;*/
                    }
                }
                else shades[0]+=2;
            }
            if(isGood(shades)) points+=4;
            shades=new int[]{0,0,0,0,0,0};
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
