package server;

import common.ChangementVisitor;
import common.viewchangement.*;

import java.rmi.RemoteException;

public class ServerSocketHandler implements ChangementVisitor {
    @Override
    public void change(CellUpdate cellUpdate) throws RemoteException {

    }

    @Override
    public void change(LoadToolCards loadToolCards) throws RemoteException {

    }

    @Override
    public void change(Move move) throws RemoteException {

    }

    @Override
    public void change(RefilledDraftPool refilledDraftPool) throws RemoteException {

    }

    @Override
    public void change(StartTurn startTurn) throws RemoteException {

    }
}
