package common.login;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;

import java.rmi.Remote;

public interface RemoteLoginManager extends Remote {
    RemoteController connect(String name, RemoteView remoteView, boolean singlePlayer) throws Exception;
}
