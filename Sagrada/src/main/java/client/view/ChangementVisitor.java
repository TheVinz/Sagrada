package client.view;

import common.viewchangement.*;

public interface ChangementVisitor {
    void change(CellUpdate cellUpdate) throws Exception;
    void change(LoadToolCards loadToolCards);
    void change(Move move) throws  Exception;
    void change(RefilledDraftPool refilledDraftPool);
    void change(StartTurn startTurn);
}
