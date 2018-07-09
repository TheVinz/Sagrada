package common.login;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The <tt>RemoteLoginManager</tt> is the interface bound to the registry by which the clients can connect via RMI to the server.
 */
public interface RemoteLoginManager extends Remote {

    /**
     * Asks the server to log in with given username, {@link RemoteView} and game modality.
     * @param name the client's username.
     * @param remoteView the client's view.
     * @param singlePlayer the boolean representing the game modality.
     * @return the RMI {@link RemoteController} created by the server and bound to given RemoteView.
     * @throws RemoteException if a connection error occurs.
     */
    RemoteController connect(String name, RemoteView remoteView, boolean singlePlayer) throws RemoteException;
}
