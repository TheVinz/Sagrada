package common.RemoteMVC;

import common.exceptions.InvalidMoveException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    int getId() throws RemoteException;
    int command(int type) throws RemoteException; //command generici
    int command(int type, int index) throws InvalidMoveException, RemoteException; //draftpool/toolCard
    int command(int type, int x, int y) throws InvalidMoveException, RemoteException; //windowframecell/roundtrackcell
}
