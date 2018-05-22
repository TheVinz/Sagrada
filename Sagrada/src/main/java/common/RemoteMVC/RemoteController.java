package common.RemoteMVC;

import common.command.GameCommand;
import common.exceptions.InvalidMoveException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
   // void getId() throws RemoteException;
    void command(GameCommand gameCommand) throws  RemoteException;
   /* void command(int type) throws InvalidMoveException, RemoteException; //command generici
    void command(int type, int index) throws InvalidMoveException, RemoteException; //draftpool/toolCard
    void command(int type, int x, int y) throws InvalidMoveException, RemoteException; //windowframecell/roundtrackcell
    */
}
