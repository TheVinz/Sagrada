package common;

import common.viewchangement.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

public interface ChangementVisitor extends Remote{
    void change(CellUpdate cellUpdate) throws RemoteException;
    void change(LoadToolCards loadToolCards) throws RemoteException;;
    void change(Move move) throws  RemoteException;
    void change(RefilledDraftPool refilledDraftPool) throws RemoteException;;
    void change(StartTurn startTurn) throws RemoteException;;
}
