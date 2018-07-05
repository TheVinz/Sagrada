package server.network;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;
import server.GameManager;
import server.viewproxy.RMIViewProxy;
import server.viewproxy.ViewProxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The <tt>RMILoginManager</tt> is the class bound to the RMIRegistry to allow remote players to connect to the server.
 */
public class RMILoginManager extends UnicastRemoteObject implements RemoteLoginManager {

    private GameManager gameManager;

    /**
     * Initializes a new RMILoginManager setting the {@link GameManager} that will handle the RMI connections.
     * @param gameManager the server's GameManager
     * @throws RemoteException
     */
    public RMILoginManager(GameManager gameManager) throws RemoteException {
        super();
        this.gameManager = gameManager;
    }

    /**
     * Method invoked by remote clients, logging him into the server with the selected username, creates a
     * new {@link ViewProxy} bound to the {@link RemoteView} and returns it to the client.
     * @param name the client's username.
     * @param remoteView the client's remote view.
     * @param singlePlayer the boolean representing the game modality chosen.
     * @return a new ViewProxy bound to the remote view.
     */
    @Override
    public RemoteController connect(String name, RemoteView remoteView, boolean singlePlayer) {
        try {
            System.out.print(name + " connected\n>>>");
            ViewProxy viewProxy = new RMIViewProxy();
            ((RMIViewProxy) viewProxy).bindRemoteView(remoteView);
            gameManager.addPlayer(name, viewProxy, singlePlayer);
            return viewProxy;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e){
            return null;
        }
    }


}
