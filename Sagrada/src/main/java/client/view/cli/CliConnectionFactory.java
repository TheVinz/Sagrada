package client.view.cli;

import client.view.network.ClientRMIConnection;
import client.view.network.ClientSocketConnection;
import common.RemoteMVC.RemoteView;

import java.util.Scanner;

public class CliConnectionFactory{

    private final String ip = "localhost";

    public CliConnectionFactory(String choice, Scanner sc, String name, RemoteView viewModel, boolean singlePlayer) {
        do {
            System.out.print("RMI or Socket? (r/s):\n>>>");
            choice = sc.nextLine();
        } while (!choice.equals("s") && !choice.equals("r"));

        if (choice.equals("r"))
            new ClientRMIConnection(ip, name, viewModel, singlePlayer);
        else
            new ClientSocketConnection(ip, name, viewModel, singlePlayer);
    }
}
