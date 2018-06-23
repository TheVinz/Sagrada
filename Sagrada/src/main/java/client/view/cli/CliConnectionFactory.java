package client.view.cli;

import client.network.ClientConnection;
import client.network.ClientRMIConnection;
import client.network.ClientSocketConnection;
import common.RemoteMVC.RemoteView;

import java.util.Scanner;

public class CliConnectionFactory{

    private final String ip = "localhost";
    ClientConnection clientConnection = null;

    public CliConnectionFactory(Scanner sc, String name, RemoteView viewModel, boolean singlePlayer) {
        String choice;
        do {
            System.out.print("RMI or Socket? (r/s):\n>>>");
            choice = sc.nextLine();
        } while (!choice.equals("s") && !choice.equals("r"));

        if (choice.equals("r"))
            clientConnection = new ClientRMIConnection(ip, name, viewModel, singlePlayer);
        else
            clientConnection = new ClientSocketConnection(ip, name, viewModel, singlePlayer);
    }

    public void close(){
        clientConnection.close();
    }
}
