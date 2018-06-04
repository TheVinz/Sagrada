package server.network;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;
import server.GameManager;
import server.model.Model;
import server.model.state.player.Player;
import server.viewproxy.RMIViewProxy;
import server.viewproxy.ViewProxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMILoginManager extends UnicastRemoteObject implements RemoteLoginManager {

    private GameManager gameManager;
    private Object lock;

    public RMILoginManager(GameManager gameManager, Object lock) throws RemoteException {
        super();
        this.gameManager = gameManager;
        this.lock = lock;
    }

    @Override
    public RemoteController connect(String name, RemoteView remoteView, boolean singlePlayer) throws Exception {
        synchronized (lock) {
            Model model = gameManager.setModel(name, singlePlayer);
            Player player = model.addPlayer(name, model.getState().getPlayers().size());
            ViewProxy viewProxy = new RMIViewProxy(model, player);
            model.addViewProxyPlayer(viewProxy, player);
            ((RMIViewProxy) viewProxy).bindRemoteView(remoteView);
            gameManager.startGame(singlePlayer);
            System.out.print(name + " connected\n>>>");
            return viewProxy;
        }
    }


}
