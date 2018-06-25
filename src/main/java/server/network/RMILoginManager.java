package server.network;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;
import server.GameManager;
import server.LaunchServer;
import server.viewproxy.RMIViewProxy;
import server.viewproxy.ViewProxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMILoginManager extends UnicastRemoteObject implements RemoteLoginManager {

    private GameManager gameManager;

    public RMILoginManager(GameManager gameManager) throws RemoteException {
        super();
        this.gameManager = gameManager;
    }

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
