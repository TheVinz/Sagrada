package server.model.state.objectivecards.publicobjectivecards;

import server.model.state.boards.windowframe.WindowFrame;

@SuppressWarnings("Duplicates")
public class DifferentShadesRow extends PublicObjectiveCard{
    public int calculatePoints(WindowFrame windowFrame) {
        int points=0;
        int[] shades={0,0,0,0,0,0};
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++) {
                if(!windowFrame.getCell(i,j).isEmpty()){
                    switch(windowFrame.getCell(i,j).getDice().getValue()){
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
            if(isGood(shades)) points+=5;
            shades=new int[]{0,0,0,0,0,0};
        }
        return points;
    }

    @Override
    public int getNumber() {
        return DIFFERENT_SHADES_ROW;
    }

    private boolean isGood(int[] colors){
        for (int color:colors) {
            if(color>1) return false;
        }
        return true;
    }
}
