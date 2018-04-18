package TestGUI.common;


import TestGUI.common.viewchangement.Changement;
import TestGUI.server.model.boards.draftpool.DraftPoolCell;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;

public interface Observer {

    void updateSimpleMove(DraftPoolCell source, WindowFrameCell target);

    void updateRefillDraftPool();
}
