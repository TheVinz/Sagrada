package server.login;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;
import server.model.Model;
import server.model.SinglePlayerModel;
import server.viewproxy.RMIViewProxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginManager extends UnicastRemoteObject implements RemoteLoginManager {

    private Model model;

    public LoginManager() throws RemoteException {
        super();
    }

    @Override
    public RemoteController connect(String name, RemoteView remoteView, boolean singlePlayer) throws Exception {
        if(model == null) //perchè manca il multipartita
            model = singlePlayer ? new SinglePlayerModel() : new Model();
        RemoteController remoteController = model.addRMIPlayer(name);
        ((RMIViewProxy) remoteController).bindRemoteView(remoteView);
        System.out.print(name + " connected\n>>>");
        return remoteController;
    }

    public void startGame() throws RemoteException {
        model.startGame();
    }

}
