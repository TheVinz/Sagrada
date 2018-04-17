package TestGUI.server.model;

import TestGUI.common.Observable;
import TestGUI.common.viewchangement.Changement;
import TestGUI.common.viewchangement.Move;
import TestGUI.common.viewchangement.RefilledDraftPool;
import TestGUI.server.model.bag.Bag;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.boards.draftpool.DraftPool;
import TestGUI.server.model.boards.draftpool.DraftPoolCell;
import TestGUI.server.model.boards.windowframe.WindowFrame;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;
import TestGUI.server.model.exceptions.InvalidMoveException;

import java.util.Arrays;

public class Model extends Observable {
    private WindowFrame windowFrame;
    private DraftPool draftPool;
    private Bag bag;


    public Model(){
        windowFrame=new WindowFrame();
        draftPool=new DraftPool(5);
        bag=new Bag();
    }

    @Override
    public void notifyObservers() {

    }

    public DraftPool getDraftPool() {
        return draftPool;
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    public void simpleMove(DraftPoolCell source, WindowFrameCell target) throws InvalidMoveException {
        source.move(target);
        notifyObservers(new Move(source.getIndex(), target.getX(), target.getY()));
    }

    public void refillDraftPool() throws InvalidMoveException {
        draftPool.drow(bag);
        notifyObservers(new RefilledDraftPool(draftPool.asImage()));
    }

    public void useToolCard() {
    }
}
