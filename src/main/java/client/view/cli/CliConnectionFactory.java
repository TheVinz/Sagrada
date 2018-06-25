package client.view.cli;

import client.network.ClientConnection;
import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CliConnectionFactory extends ClientConnection{

    String ip = "localhost";
    public CliConnectionFactory(Scanner sc, String name, RemoteView viewModel, boolean singlePlayer) {
        String choice;
        do {
            System.out.print("RMI or Socket? (r/s):\n>>>");
            choice = sc.nextLine();
        } while (!choice.equals("s") && !choice.equals("r"));

        if (choice.equals("r")) {
            try {
                connectRmi(ip, name, viewModel, singlePlayer);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
        else
            connectSocket(ip, viewModel, name, singlePlayer);
    }

    @Override
    public void setRemoteController(RemoteController remoteController) {
        CliApp.getCliApp().setRemoteController(remoteController);
    }

    @Override
    public void notifyDisconnection() {
        CliApp.getCliApp().suspend();
    }

}
