package TestGUI.common;



import TestGUI.common.viewchangement.Changement;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.boards.draftpool.DraftPoolCell;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
    void notifyMove(Cell source, Cell target);

    void notifyRefillDraftPool();
    void updateCell(Cell cell);
}
