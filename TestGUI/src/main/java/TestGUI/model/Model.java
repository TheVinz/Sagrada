package TestGUI.model;

import TestGUI.model.bag.Bag;
import TestGUI.model.boards.draftpool.DraftPool;
import TestGUI.model.boards.windowframe.WindowFrame;

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

    public void drawDraftPool(){
        draftPool.clean();
        draftPool.drow(bag);
    }
}
