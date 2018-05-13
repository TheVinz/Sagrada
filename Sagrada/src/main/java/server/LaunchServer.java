package server;

import common.login.RemoteLoginManager;
import server.login.LoginManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LaunchServer {
    public static void main(String[] args){
        RemoteLoginManager loginManager = new LoginManager();
        try {
            Registry reg = LocateRegistry.getRegistry();
            System.out.println("Starting server...");
            reg.rebind("LoginManager", loginManager);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Server on");
    }
}
