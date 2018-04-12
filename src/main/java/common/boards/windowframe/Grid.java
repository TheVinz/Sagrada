package common.boards.windowframe;


import java.util.Scanner;

import static common.utilities.Color.*;

public enum Grid {
    KALEIDOSCOPIC_DREAM(4, "yb001g050430r0g200by"), VIRTUS(5, "4025g006g203g405g100"),
    AURORAE_MAGNIFICUS(5, "5gbp2p000yy060p100g4"), VIA_LUX(4, "y0600015023yrp00043r"),
    SUN_CATCHER(3, "0b20y040r0005y0g300p"), BELLESGUARD(3, "b600y03b00056200401g"),
    FIRMITAS(5, "p60035p30002p10015p4"), SYMPHONY_OF_LIGHT(6, "20501y6p2r0b5g003050"),


    private WindowFrameCell[][] cells;
    private int favorToken;
    public static final int ROWS=4;
    public static final int COLUMNS=5;

    Grid(int tokens, String rep){
        this.favorToken=tokens;
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLUMNS; j++){
                switch(rep.charAt(i*COLUMNS+j)){
                    case 'y':
                        cells[i][j]=new WindowFrameCell(YELLOW);
                        break;
                    case 'b' :
                        cells[i][j]=new WindowFrameCell(BLUE);
                        break;
                    case 'g' :
                        cells[i][j]=new WindowFrameCell(GREEN);
                        break;
                    case 'r' :
                        cells[i][j]=new WindowFrameCell(RED);
                        break;
                    case 'p' :
                        cells[i][j]=new WindowFrameCell(PURPLE);
                        break;
                    case '1' :
                        cells[i][j]=new WindowFrameCell(1);
                        break;
                    case '2' :
                        cells[i][j]=new WindowFrameCell(2);
                        break;
                    case '3' :
                        cells[i][j]=new WindowFrameCell(3);
                        break;
                    case '4' :
                        cells[i][j]=new WindowFrameCell(4);
                        break;
                    case '5' :
                        cells[i][j]=new WindowFrameCell(5);
                        break;
                    case '6' :
                        cells[i][j]=new WindowFrameCell(6);
                        break;
                    default :
                        cells[i][j]=new WindowFrameCell();
                }
            }
        }
    }

    public WindowFrameCell[][] getGrid(){
        return this.cells;
    }

    public int getFavorToken() {
        return favorToken;
    }
}
