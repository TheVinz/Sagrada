package TestGUI.server.model;

import TestGUI.server.model.bag.Bag;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.boards.draftpool.DraftPool;
import TestGUI.server.model.boards.windowframe.WindowFrame;
import TestGUI.server.model.exceptions.InvalidMoveException;

public class Model {
    private WindowFrame windowFrame;
    private DraftPool draftPool;
    private Bag bag;

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

    public void move(Cell source, Cell target) throws InvalidMoveException {
        source.move(target);
    }

    public void refillDraftPool() throws InvalidMoveException {
        draftPool.drow(bag);
    }

    public void useToolCard() {
    }
}
