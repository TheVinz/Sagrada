package TestGUI.model;

import TestGUI.model.bag.Bag;
import TestGUI.model.boards.Cell;
import TestGUI.model.boards.draftpool.DraftPool;
import TestGUI.model.boards.windowframe.WindowFrame;
import TestGUI.model.exceptions.InvalidMoveException;

public class Model {
    private WindowFrame windowFrame;
    private DraftPool draftPool;
    private Bag bag;

    private Cell picked;

    public Model(){
        windowFrame=new WindowFrame();
        draftPool=new DraftPool(5);
        bag=new Bag();
    }

    public DraftPool getDraftPool() {
        return draftPool;
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    public void drawDraftPool(){
        draftPool.clean();
        draftPool.drow(bag);
    }

    public void pickFromWindow(Integer rowIndex, Integer columnIndex) {
        picked=windowFrame.getCell(rowIndex,columnIndex);
    }

    public void pickFromDraftPool(Integer columnIndex) {
        picked=draftPool.getDraftPool()[columnIndex];
    }

    public void moveToDraftPool(Integer columnIndex) throws InvalidMoveException {
        Cell source=picked;
        picked=null;
        source.move(draftPool.getDraftPool()[columnIndex]);
    }

    public void moveToWindow(Integer rowIndex, Integer columnIndex) throws InvalidMoveException {
        Cell source=picked;
        picked=null;
        source.move(windowFrame.getCell(rowIndex,columnIndex));
    }

    public void cancelMove() {
        picked=null;
    }
}
