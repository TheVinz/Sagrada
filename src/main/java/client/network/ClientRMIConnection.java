package client.network;

import client.view.cli.CliApp;
import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMIConnection implements ClientConnection{

    RemoteLoginManager loginManager;
    RemoteController remoteController;
    RemoteView viewModel;

    public ClientRMIConnection(String ip, String name, RemoteView viewModel, boolean singlePlayer){
        this.viewModel = viewModel;
        try {
            Registry reg = LocateRegistry.getRegistry();
            loginManager=(RemoteLoginManager) reg.lookup("RMILoginManager");
            remoteController = loginManager.connect(name, viewModel, singlePlayer);
            CliApp.getCliApp().setRemoteController(remoteController);
            new Thread(() -> {
                while(true) {
                    try {
                        remoteController.command(null);
                        Thread.sleep(1000);
                    } catch (IOException e) {
                        CliApp.getCliApp().suspend();
                        return;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }).start();
        } catch (RemoteException e) {
            System.err.println("Connection error");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void close() {
        loginManager = null;
        remoteController = null;
        viewModel = null;
    }
}
