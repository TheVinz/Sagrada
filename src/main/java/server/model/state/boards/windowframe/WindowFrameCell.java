package server.model.state.boards.windowframe;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.Cell;
import server.model.state.utilities.Color;

import static server.model.state.ModelObject.ModelType.WINDOW_FRAME_CELL;

/**
 * The <tt>WindowFrameCell</tt> class extends the {@link server.model.state.boards.Cell} class.
 * Represents the cell in the {@link server.model.state.boards.windowframe.WindowFrame}.
 * They might have a color restriction or a value restriction.
 *
 */
public class WindowFrameCell extends Cell implements ModelObject {

    private Color color;
    private  final int row;
    private  final int columnn;
    private int shade;

    /**
     * Initialize a WindowFrameCell without color or value restriction.
     * @param row row of the matrix that indicates the WindowFrame.
     * @param col column of the matrix that indicates the WindowFrame.
     */
    public WindowFrameCell(int row, int col){
        this.color=null;
        this.shade=0;
        this.row=row;
        this.columnn=col;
    }

    /**
     * Initialize a WindowFrameCell with color restriction.
     * @param color indicates the restriction of the cell.
     * @param row row of the matrix that correspond to the WindowFrame.
     * @param columnn column of the matrix that correspond to the WindowFrame.
     */
    public WindowFrameCell(Color color, int row, int columnn){
        this.color=color;
        this.shade=0;
        this.row=row;
        this.columnn=columnn;
    }

    /**
     * Initialize a WindowFrameCell with a value restriction.
     * @param shade indicates the value restriction of the cell.
     * @param row row of the matrix that correspond to the WindowFrame.
     * @param columnn column of the matrix that correspond to the WindowFrame.
     */
    public WindowFrameCell(int shade, int row, int columnn){
        this.color=null;
        this.shade=shade;
        this.row=row;
        this.columnn=columnn;
    }

    /**
     * Gets the row in the WindowFrame of this WindowFrameCell.
     * @return row of this cell in the WindowFrame.
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Gets the column in the WindowFrame of this WindowFrameCell.
     * @return column of this cell in the WindowFrame.
     */
    public int getColumnn() {
        return columnn;
    }

    /**
     * Gets the value that represents the value restriction of this cell.
     * @return the value of this cell.
     */
    public int getShade() {
        return shade;
    }

    /**
     * Gets the color that represents the color restriction of this cell.
     * @return the color of this cell.
     */
    public Color getColor() {
        return color;
    }


    /**
     * Gets the type of this object.
     * @return WINDOW_FRAME_CELL type.
     */
    @Override
    public ModelType getType() {
        return WINDOW_FRAME_CELL;
    }
}
