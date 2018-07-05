package client.view.cli;

import client.settings.Settings;
import common.RemoteMVC.RemoteView;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CliLaunchClient {

    private static Scanner sc;
    private static RemoteView viewModel;

    public static void main(String[] args){
        sc = new Scanner(System.in);
        System.out.print("\t\t\t\tWelcome to:\t");
        CliDisplayer.getDisplayer().printBold("Sagrada");
        connect();
        try {
            UnicastRemoteObject.unexportObject(viewModel, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }

    public static void reconnect(Scanner sc, String name){
        try {
            viewModel = CliModel.getCliModel(false);
           new CliConnectionFactory(sc, name, viewModel, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void connect(){
        System.out.print("\nUsername:\n>>> ");
        String name = sc.nextLine();
        Settings.save(Settings.getServerIp(), name);
        String choice = null;
        do {
            System.out.print("Single Player? (y/n):\n>>>");
            choice = sc.nextLine();
        }while(!choice.equals("y")&&!choice.equals("n"));

        boolean singlePlayer = choice.equals("y");


        try {
            if (!singlePlayer)
                viewModel = CliModel.getCliModel(false);
            else
                viewModel = CliModel.getCliModel(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        new CliConnectionFactory(sc, name, viewModel, singlePlayer);
        CliApp.getCliApp().mainLoop();
        sc.close();
       // System.exit(1);
    }
}