package server.state.util;


//Classe con soli metodi statici, utile per non fare troppo casino nel controller mettendoci 20 mila metodi
//I metodi qui implementati sono per lo più boolean per verificare la validità di una mossa

import server.state.boards.Cell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.utilities.Color;

public class GameRules {

    public static boolean validFirstMove(WindowFrame windowFrame, WindowFrameCell cell) {
        return getUp(windowFrame, cell) == null || getRight(windowFrame, cell) == null
                || getLeft(windowFrame, cell) == null || getDown(windowFrame, cell) == null;
    }

    public static boolean validAdjacentDices(WindowFrame windowFrame, WindowFrameCell target) {
        return diceOK(getDown(windowFrame, target)) || diceOK(getUp(windowFrame, target))
                || diceOK(getLeft(windowFrame, target)) || diceOK(getRight(windowFrame, target));
    }

    public static boolean validAdjacentDiceColors(WindowFrame windowFrame, Color color, WindowFrameCell cell){
        return colorOK(color, getUp(windowFrame, cell)) && colorOK(color, getDown(windowFrame, cell)) &&
                colorOK(color, getRight(windowFrame, cell)) && colorOK(color, getLeft(windowFrame, cell));
    }


    public static boolean validAdjacentShapes(WindowFrame windowFrame, int shape, WindowFrameCell cell) {
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
