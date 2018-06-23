package client.view.cli;

import common.RemoteMVC.RemoteView;

import java.rmi.RemoteException;
import java.util.Scanner;

public class CliLaunchClient {

    private static CliConnectionFactory cliConnectionFactory;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("\t\t\t\tWelcome to:\t");
        CliDisplayer.getDisplayer().printBold("Sagrada");
        System.out.print("\nUsername:\n>>> ");
        String name = sc.nextLine();
        String choice = null;
        do {
            System.out.print("Single Player? (y/n):\n>>>");
            choice = sc.nextLine();
        }while(!choice.equals("y")&&!choice.equals("n"));

        boolean singlePlayer = choice.equals("y");

        RemoteView viewModel=null;

        try {
            if (!singlePlayer)
                viewModel = new CliModel(false);
            else
                viewModel = new CliModel(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        cliConnectionFactory = new CliConnectionFactory(sc, name, viewModel, singlePlayer);
        CliApp.getCliApp().mainLoop();
        sc.close();
        cliConnectionFactory.close();
        System.exit(1);
    }

    public static void reconnect(Scanner sc, String name){
        try {
            cliConnectionFactory.close();
            cliConnectionFactory = new CliConnectionFactory(sc, name, new CliModel(false), false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}