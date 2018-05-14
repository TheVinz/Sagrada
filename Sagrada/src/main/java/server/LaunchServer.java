package server;

import common.login.RemoteLoginManager;
import server.login.LoginManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class LaunchServer {
    public static void main(String[] args){
        try {
            System.out.print(">>>");
            RemoteLoginManager loginManager = new LoginManager();
            Registry reg = LocateRegistry.getRegistry();
            System.out.print("Starting server...\n>>>");
            reg.rebind("LoginManager", loginManager);
            System.out.print("Server on\n>>>");
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            while(!command.equals("start"))
                command=sc.nextLine();
            ((LoginManager) loginManager).startGame();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
