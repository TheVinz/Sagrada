package client.view.cli;

import common.RemoteMVC.RemoteView;

import java.rmi.RemoteException;
import java.util.Scanner;

public class CliLaunchClient {
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

        new CliConnectionFactory(choice, sc, name, viewModel, singlePlayer);
        CliApp.getCliApp().mainLoop();
    }
}