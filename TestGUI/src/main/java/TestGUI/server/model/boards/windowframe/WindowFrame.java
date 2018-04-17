package TestGUI.server.model.boards.windowframe;

import java.util.ArrayList;

public class WindowFrame {

	private final ArrayList<WindowFrameCell> cells;
	public static final int ROWS=4;
	public static final int COLUMNS=5;

	public WindowFrame(){
		cells= new ArrayList<>();
		for(int i = 0; i<ROWS; i++){
			for(int j=0; j<COLUMNS; j++){
				cells.add(COLUMNS*i+j, new WindowFrameCell(i,j));
			}
		}
	}

	public WindowFrameCell getCell(int x, int y){
		return cells.get(x*COLUMNS+y);
	}
}