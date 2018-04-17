package common.objectivecards.publicobjectivecards;

import common.boards.windowframe.WindowFrame;

public class DifferentShades extends PublicObjectiveCard {
    public int calculatePoints(WindowFrame windowFrame) {
        int[] shades= {0,0,0,0,0,0};
        int min;
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
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
                        default:
                            break;
                    }
                }
            }
        }
        min=shades[0];
        for(int i=1; i<shades.length; i++){
            if(shades[i]<min) min=shades[i];
        }
        return min*5;
    }
}
