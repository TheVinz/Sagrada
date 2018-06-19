package common.login;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteLoginManager extends Remote {
    RemoteController connect(String name, RemoteView remoteView, boolean singlePlayer) throws RemoteException;
}
