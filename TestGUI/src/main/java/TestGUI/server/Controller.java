package TestGUI.server;

import TestGUI.server.model.Model;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.exceptions.InvalidMoveException;

public class Controller {
    private Model model;

    private Cell picked;

    public Controller(Model model){
        this.model=model;
    }

    public void pickWindowFrameCell(Integer row, Integer column) {
        picked=model.getWindowFrame().getCell(row,column);
    }

    public void pickDraftPoolCell(Integer index) {
        picked=model.getDraftPool().get(index);
    }

    public void moveToWindowFrame(int row, int column) throws InvalidMoveException {
        Cell source=picked;
        picked=null;
        model.move(source, model.getWindowFrame().getCell(row, column));
    }

    public void moveToDraftPool(int index) throws InvalidMoveException {
        Cell source=picked;
        picked=null;
        model.move(source, model.getDraftPool().get(index));
    }

    public void refillDraftPool() throws InvalidMoveException {
        if(!model.getDraftPool().isEmpty()) throw new InvalidMoveException("Not Empty Draft Pool");
        else model.refillDraftPool();
    }

    public void cancelMove(){
        picked=null;
    }



}
