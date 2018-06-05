package server.model.state.utilities;


//Classe con soli metodi statici, utile per non fare troppo casino nel controller mettendoci 20 mila metodi
//I metodi qui implementati sono per lo più boolean per verificare la validità di una mossa

import server.model.state.boards.Cell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.dice.Dice;

/**
 * The <tt>GameRules</tt> class implements methods useful to verify the validity of a move according to the game rules.
 * @see WindowFrame
 * @see WindowFrameCell
 * @see Dice
 */
public class GameRules {

    /**
     * Verify that the first move is on the boards of the WindowFrame.
     * @param windowFrame of the player.
     * @param cell of the WindowFrame.
     * @return true if the cell is on the board, false if it isn't.
     */
    public static boolean validFirstMove(WindowFrame windowFrame, WindowFrameCell cell) {
        return getUp(windowFrame, cell) == null || getRight(windowFrame, cell) == null
                || getLeft(windowFrame, cell) == null || getDown(windowFrame, cell) == null;
    }

    /**
     * Verify that a dice can be places into a WindowFrameCell respecting all the restrictions.
     * @param dice to put in the cell.
     * @param cell the WindowFrameCell.
     * @return true if it can be placed, false if it doesn't.
     */
    public static boolean validAllCellRestriction(Dice dice, WindowFrameCell cell){
        return validCellColor(dice, cell) && validCellShade(dice, cell) && cell.isEmpty();
    }

    /**Verify that a dice respects the value restriction.
     * @param dice to put in the cell.
     * @param cell the WindowFrameCell.
     * @return true if the dice respects the restriction, false if it doesn't.
     */
    public static boolean validCellShade(Dice dice, WindowFrameCell cell){
        if(cell.getShade()==0) return true;
        else return dice.getValue()==cell.getShade();
    }

    /**
     * Verify that a dice respects the color restriction.
     * @param dice to put in the cell.
     * @param cell the WindowFrameCell.
     * @return true if the dice respects the restriction, false if it doesn't.
     */
    public static boolean validCellColor(Dice dice, WindowFrameCell cell){
        if(cell.getColor()==null) return true;
        else return dice.getColor() == cell.getColor();
    }

    /**
     * Verify that putting a dice in a WindowFrameCell respects all the restriction with adjacent dice.
     * @param windowFrame where to put the dice.
     * @param dice to put in the cell.
     * @param cell of the WindowFrame.
     * @return true if the dice respects the restriction, false if it doesn't.
     */
    public static boolean validAllDiceRestriction(WindowFrame windowFrame, Dice dice, WindowFrameCell cell){
        return validAdjacentDices(windowFrame, cell) && validAdjacentDiceColors(windowFrame, dice, cell)
                && validAdjacentShapes(windowFrame, dice, cell);
    }

    /**
     * Verify if the adjacent cell of the cell where we wanna put a dice are empty. If they've got a dice return true.
     * @param windowFrame of the player.
     * @param target where we wanna put the dice.
     * @return true if an adjacent cell has got a dice, false if they haven't.
     */
    public static boolean validAdjacentDices(WindowFrame windowFrame, WindowFrameCell target) {
        return diceOK(getDown(windowFrame, target)) || diceOK(getUp(windowFrame, target))
                || diceOK(getLeft(windowFrame, target)) || diceOK(getRight(windowFrame, target)) ||
                diceOK(getUpRight(windowFrame, target)) || diceOK(getUpLeft(windowFrame, target))
                || diceOK(getDownRight(windowFrame, target)) || diceOK(getDownLeft(windowFrame, target));
    }


    /**Verify that the dice we wanna put respects the color restriction with adjacent dices, meaning that there cannot be
     * adjacent dice with same color.
     * @param windowFrame of the player.
     * @param dice to put in the cell.
     * @param cell where we wanna put the dice.
     * @return true if the dice respects the color restriction, false if it doesn't.
     */
    public static boolean validAdjacentDiceColors(WindowFrame windowFrame, Dice dice, WindowFrameCell cell){
        Color color = dice.getColor();
        return colorOK(color, getUp(windowFrame, cell)) && colorOK(color, getDown(windowFrame, cell)) &&
                colorOK(color, getRight(windowFrame, cell)) && colorOK(color, getLeft(windowFrame, cell));
    }


    public static boolean validAdjacentShapes(WindowFrame windowFrame,Dice dice, WindowFrameCell cell) {
        int shape=dice.getValue();
        return shapeOK(shape, getUp(windowFrame, cell)) && shapeOK(shape, getDown(windowFrame, cell)) &&
                shapeOK(shape, getRight(windowFrame, cell)) && shapeOK(shape, getLeft(windowFrame, cell));
    }

    private static boolean shapeOK(int shape, Cell cell) {
        if(cell==null) return true;
        else if(cell.isEmpty()) return true;
        else return shape!=cell.getDice().getValue();
    }

    private static WindowFrameCell getLeft(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getColumnn()==0) return null;
        else return windowFrame.getCell(cell.getRow(), cell.getColumnn() - 1);
    }

    private static WindowFrameCell getRight(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getColumnn()==4) return null;
        else return windowFrame.getCell(cell.getRow(), cell.getColumnn() +1);
    }

    private static WindowFrameCell getDown(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getRow()==3) return null;
        else return windowFrame.getCell(cell.getRow() + 1, cell.getColumnn());
    }

    private static WindowFrameCell getUp(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getRow()==0) return null;
        else return windowFrame.getCell(cell.getRow()-1, cell.getColumnn());
    }

    private static Cell getUpRight(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getRow()==0) return null;
        if(cell.getColumnn()==4) return null;
        else return windowFrame.getCell(cell.getRow()-1, cell.getColumnn()+1);
    }

    private static Cell getUpLeft(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getRow()==0) return null;
        if(cell.getColumnn()==0) return null;
        else return windowFrame.getCell(cell.getRow()-1, cell.getColumnn()-1);
    }

    private static Cell getDownLeft(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getRow()==3) return null;
        if(cell.getColumnn()==0) return null;
        else return windowFrame.getCell(cell.getRow()+1, cell.getColumnn()-1);
    }

    private static Cell getDownRight(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getRow()==3) return null;
        if(cell.getColumnn()==4) return null;
        else return windowFrame.getCell(cell.getRow()+1, cell.getColumnn()+1);
    }

    private static boolean diceOK(Cell cell){
        if(cell==null) return false;
        else return(!cell.isEmpty());
    }

    private static boolean colorOK(Color color, Cell cell) {
        if(cell==null) return true;
        else if(cell.isEmpty())  return true;
        else return cell.getDice().getColor() != color;
    }
}
