package common.RemoteMVC;

import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    int getId() throws RemoteException;
    void command(Response type) throws InvalidMoveException, RemoteException; //command generici
    void command(Response type, int index) throws InvalidMoveException, RemoteException; //draftpool/toolCard
    void command(Response type, int x, int y) throws InvalidMoveException, RemoteException; //windowframecell/roundtrackcell
}
