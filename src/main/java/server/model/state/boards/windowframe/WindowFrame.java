package server.model.state.boards.windowframe;

import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.utilities.Color;

/**
 * The <tt>WindowFrame</tt> class represents the window where the players will put the {@link server.model.state.dice.Dice}s.
 * This window is a matrix of {@link server.model.state.boards.windowframe.WindowFrameCell}. A matrix with 4 rows and 5 columns.
 * There are twenty WindowFrame defined in the class {@link server.model.state.boards.windowframe.WindowFrameList}.
 * Every WindowFrame has a different difficulty defined from FavorTokens.
 * @see WindowFrameCell
 */
public class WindowFrame implements ModelObject {


	private final WindowFrameCell[][] cells;
	private final WindowFrameList windowFrameList;
	public static final int ROWS=4;
	public static final int COLUMNS=5;

	/**
	 * Initialize a WindowFrame, a matrix of WindowFrameCell, given a rep. The rep is a string that describe each WindowFrame
	 * in the class WindowFrameList. Indicates if a cell has got a colour restriction or a value restriction or nothing.
	 * @param windowFrameList a defined WindowFrame with a rep.
	 */
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

	/**
	 * Gets the FavorToken of this WindowFrame. The FavorTokens are useful for {@link server.model.state.toolcards.ToolCard}.
	 * @return an int that indicates the difficulty to complete this WindowFrame.
	 */
	public int getFavorToken() {
		return windowFrameList.getFavorToken();
	}

	/**
	 * Gets a WindowFrameCell given the row and the column.
	 * @param row of the cell i wanna get.
	 * @param column of the cell i wanna get.
	 * @return a WindowFrameCell.
	 */
	public WindowFrameCell getCell(int row, int column){
		return cells[row][column];
	}

	/**
	 * Gets the rep, a string that defines the WindowFrame initial.
	 * @return a string of twenty characters.
	 */
	public String getRep(){return windowFrameList.getRep();}

	/**
	 * Gets the type of this object,
	 * @return WINDOW_FRAME type.
	 */
	@Override
	public ModelType getType() {
		return ModelType.WINDOW_FRAME;
	}

	/**
	 * Gets the sum of the empty cells in the WindowFrame. Useful for calculate the points at the end of the game.
	 * @return the sum of empty cells in the WindowFrame.
	 */
	public int getEmptyCells(){
		int res = 0;
		for(int i=0;i<ROWS;i++){
			for(int j=0;j<COLUMNS;j++){
				if(getCell(i,j).isEmpty())
					res++;
			}
		}
		return res;
	}
}