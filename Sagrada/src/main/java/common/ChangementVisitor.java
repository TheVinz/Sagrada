package common;

import common.viewchangement.*;

import java.rmi.Remote;

public interface ChangementVisitor extends Remote {
    void change(CellUpdate cellUpdate);

    void change(LoadToolCards loadToolCards);

    void change(Move move);

    void change(RefilledDraftPool refilledDraftPool);

    void change(StartTurn startTurn);
}
