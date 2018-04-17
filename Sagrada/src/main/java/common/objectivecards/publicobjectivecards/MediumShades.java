package common.objectivecards.publicobjectivecards;

import common.boards.windowframe.WindowFrame;

public class MediumShades extends PublicObjectiveCard {
    public int calculatePoints(WindowFrame windowFrame) {
        int[] shades= {0,0};
        for(int i=0; i<WindowFrame.ROWS; i++){
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if(!windowFrame.getCell(i,j).isEmpty()){
                    switch(windowFrame.getCell(i,j).getDice().getValue()){
                        case 3:
                            shades[0]++;
                            break;
                        case 4:
                            shades[1]++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        if(shades[0]<shades[1]) return 2*shades[0];
        else return shades[1]*2;
    }
}
