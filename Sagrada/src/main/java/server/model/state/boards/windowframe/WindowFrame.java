package server.model.state.boards.windowframe;

import common.ModelObject;
import server.model.state.utilities.Color;

public class WindowFrame implements ModelObject {


	private final WindowFrameCell[][] cells;
	private final WindowFrameList windowFrameList;
	public static final int ROWS=4;
	public static final int COLUMNS=5;

	public WindowFrame(WindowFrameList windowFrameList){
		this.windowFrameList = windowFrameList;
		cells=new WindowFrameCell[ROWS][COLUMNS];
		for(int i=0; i<ROWS; i++){
			for(int j=0; j<COLUMNS; j++){
				switch(windowFrameList.getRep().charAt(i*COLUMNS+j)){
					case 'y':
						cells[i][j]=new WindowFrameCell(Color.YELLOW,i,j);
						break;
					case 'b' :
						cells[i][j]=new WindowFrameCell(Color.BLUE,i,j);
						break;
					case 'g' :
						cells[i][j]=new WindowFrameCell(Color.GREEN,i,j);
						break;
					case 'r' :
						cells[i][j]=new WindowFrameCell(Color.RED,i,j);
						break;
					case 'p' :
						cells[i][j]=new WindowFrameCell(Color.PURPLE,i,j);
						break;
					case '1' :
						cells[i][j]=new WindowFrameCell(1,i,j);
						break;
					case '2' :
						cells[i][j]=new WindowFrameCell(2,i,j);
						break;
					case '3' :
						cells[i][j]=new WindowFrameCell(3,i,j);
						break;
					case '4' :
						cells[i][j]=new WindowFrameCell(4,i,j);
						break;
					case '5' :
						cells[i][j]=new WindowFrameCell(5,i,j);
						break;
					case '6' :
						cells[i][j]=new WindowFrameCell(6,i,j);
						break;
					default :
						cells[i][j]=new WindowFrameCell(i,j);
				}
			}
		}
	}
/*
	public WindowFrameCell[][] getGrid(){
		return this.cells;
	}
*/
	public int getFavorToken() {
		return windowFrameList.getFavorToken();
	}

	public WindowFrameCell getCell(int row, int column){
		return cells[row][column];
	}

	public String getRep(){return windowFrameList.getRep();}

	@Override
	public int getType() {
		return WINDOW_FRAME;
	}
}