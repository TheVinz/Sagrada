package client.network;

import client.view.cli.CliApp;
import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMIConnection {

    RemoteLoginManager loginManager;
    RemoteController remoteController;

    public ClientRMIConnection(String ip, String name, RemoteView viewModel, boolean singlePlayer){
        try {
            Registry reg = LocateRegistry.getRegistry();
            loginManager=(RemoteLoginManager) reg.lookup("RMILoginManager");
            remoteController = loginManager.connect(name, viewModel, singlePlayer);
            CliApp.getCliApp().setRemoteController(remoteController);
        } catch (RemoteException e) {
            System.err.println("Connection error");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
