package common.boards.windowframe;

import static common.utilities.Color.*;

public enum WindowFrame {
	KALEIDOSCOPIC_DREAM(4, "yb001g050430r0g200by"), VIRTUS(5, "4025g006g203g405g100"),
	AURORAE_MAGNIFICUS(5, "5gbp2p000yy060p100g4"), VIA_LUX(4, "y0600015023yrp00043r"),
	SUN_CATCHER(3, "0b20y040r0005y0g300p"), BELLESGUARD(3, "b600y03b00056200401g"),
	FIRMITAS(5, "p60035p30002p10015p4"), SYMPHONY_OF_LIGHT(6, "20501y6p2r0b5g003050"),
	AURORA_SAGRADIS(4, "r0b0y4p3g2010500600"), INDUSTRIA(5, "1r30654r20005r10003r"),
	SHADOW_THIEF(5, "6p00550p00r60p0yr543"), BATLLO(5, "0060005b403gyp214r53"),
	GRAVITAS(5, "103b002b006b040b5201"), FRACTAL_DROPS(3, "040y6r020000rp1by000"),
	LUX_ASTRAL(5, "01rp46p25g1g53p00000"), CHROMATIC_SPLENDOR(4, "00g002y5b10r3p010604"),
	FIRELIGHT(5, "341500620y000yr50yr6"), LUZ_CELESTIAL(3, "00r50p40g3600b00y200"),
	WATER_OF_LIFE(6, "6b00105b004r2b0g6y3p"), RIPPLES_OF_LIGHT(5, "000r500p4b0b3y6y2g1r"),
	LUX_MUNDI(6, "001001g3b2g546g0b5g0"), COMITAS(5, "y02060405y000y512y30"),
	SUNS_GLORY(6, "1py04py006y005305421"), FULGOR_DEL_CIELO(5, "0br000450bb20r56r310");


	private final WindowFrameCell[][] cells;
	private final int favorToken;
	public static final int ROWS=4;
	public static final int COLUMNS=5;

	WindowFrame(int tokens, String rep){
		this.favorToken=tokens;
		cells=new WindowFrameCell[ROWS][COLUMNS];
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

	public WindowFrameCell getCell(int row, int column){
		return cells[row][column];
	}
}