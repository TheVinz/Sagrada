package TestGUI.server;

import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.boards.windowframe.WindowFrame;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;
import javafx.scene.paint.Color;

//Classe con soli metodi statici, utile per non fare troppo casino nel controller mettendoci 20 mila metodi
//I metodi qui implementati sono per lo più boolean per verificare la validità di una mossa

public class GameRules {

    public static boolean validFirstMove(WindowFrame windowFrame, WindowFrameCell cell) {
        if(getUp(windowFrame, cell)==null || getRight(windowFrame, cell)==null
                || getLeft(windowFrame, cell)==null || getDown(windowFrame, cell)==null)
            return true;
        else return false;
    }

    public static boolean validAdjacentDices(WindowFrame windowFrame, WindowFrameCell target) {
        if(diceOK(getDown(windowFrame, target)) || diceOK(getUp(windowFrame, target))
                || diceOK(getLeft(windowFrame, target)) || diceOK(getRight(windowFrame, target)))
            return true;
        else return false;
    }

    private static boolean diceOK(Cell cell){
        if(cell==null) return false;
        else return(!cell.isEmpty());
    }

    public static boolean validAdjacentDiceColors(WindowFrame windowFrame, Color color, WindowFrameCell cell){
        if(colorOK(color, getUp(windowFrame, cell)) && colorOK(color, getDown(windowFrame, cell)) &&
                colorOK(color, getRight(windowFrame, cell)) && colorOK(color, getLeft(windowFrame, cell)))
            return true;
        else return false;
    }

    private static boolean colorOK(Color color, Cell cell) {
        if(cell==null) return true;
        else if(cell.isEmpty())  return true;
        else return cell.getDice().getColor() != color;
    }

    public static boolean validAdjacentShapes(WindowFrame windowFrame, int shape, WindowFrameCell cell) {
        if(shapeOK(shape, getUp(windowFrame, cell)) && shapeOK(shape, getDown(windowFrame, cell)) &&
                shapeOK(shape, getRight(windowFrame, cell)) && shapeOK(shape, getLeft(windowFrame, cell)))
            return true;
        else return false;
    }

    private static boolean shapeOK(int shape, Cell cell) {
        if(cell==null) return true;
        else if(cell.isEmpty()) return true;
        else return shape!=cell.getDice().getValue();
    }

    public static WindowFrameCell getLeft(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getY()==0) return null;
        else return windowFrame.getCell(cell.getX(), cell.getY() - 1);
    }

    public static WindowFrameCell getRight(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getY()==4) return null;
        else return windowFrame.getCell(cell.getX(), cell.getY() +1);
    }

    public static WindowFrameCell getDown(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getX()==3) return null;
        else return windowFrame.getCell(cell.getX() + 1, cell.getY());
    }

    public static WindowFrameCell getUp(WindowFrame windowFrame, WindowFrameCell cell) {
        if(cell.getX()==0) return null;
        else return windowFrame.getCell(cell.getX()-1, cell.getY());
    }



}
