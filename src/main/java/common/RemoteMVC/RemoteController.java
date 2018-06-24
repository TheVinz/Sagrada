package common.RemoteMVC;

import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    //int getId() throws RemoteException;
    /*void command(Response type) throws RemoteException; //command generici
    void command(Response type, int index) throws RemoteException; //draftpool/toolCard
    void command(Response type, int x, int y) throws RemoteException; //windowframecell/roundtrackcell*/

    void command(GameCommand gameCommand) throws IOException;

}
