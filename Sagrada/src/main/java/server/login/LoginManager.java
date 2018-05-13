package server.login;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;
import server.model.Model;

public class LoginManager implements RemoteLoginManager {

    private Model model;

    public LoginManager(){
        this.model= new Model();
    }

    @Override
    public RemoteController connect(String name, RemoteView remoteView) throws Exception {
        RemoteController remoteController = model.addRMIPlayer(name);
        return remoteController;
    }

    @Override
    public void start() {
        model.startGame();
    }

}
