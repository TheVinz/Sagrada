package server.network;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;
import server.GameManager;
import server.model.Model;
import server.model.SinglePlayerModel;
import server.model.state.player.Player;
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
    public RemoteController connect(String name, RemoteView remoteView, boolean singlePlayer) throws Exception {
        gameManager.setModel(singlePlayer);
        Player player = gameManager.getModel().addPlayer(name, gameManager.getModel().getState().getPlayers().size());
        ViewProxy viewProxy = new RMIViewProxy(gameManager.getModel(), player);
        gameManager.getModel().addViewProxyPlayer(viewProxy, player);
        ((RMIViewProxy) viewProxy).bindRemoteView(remoteView);
        System.out.print(name + " connected\n>>>");
        return viewProxy;
    }


}
