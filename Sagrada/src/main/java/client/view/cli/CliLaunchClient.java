package client.view.cli;

import common.RemoteMVC.RemoteController;
import common.login.RemoteLoginManager;
import server.login.LoginManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CliLaunchClient {
    public static void main(String[] args){

        RemoteLoginManager loginManager;
        CliModel model;
        RemoteController remoteController;
        CliApp app;

        Scanner sc = new Scanner(System.in);
        System.out.println("\t\t\t\tWelcome to SAGRADA\n");
        System.out.print("Username>>> ");
        String name = sc.nextLine();


        try {
            model= new CliModel();
            Registry reg = LocateRegistry.getRegistry();
            loginManager=(RemoteLoginManager) reg.lookup("LoginManager");
            remoteController = loginManager.connect(name, model);
            CliApp.getCliApp().setRemoteController(remoteController);
            CliApp.getCliApp().mainLoop();
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
