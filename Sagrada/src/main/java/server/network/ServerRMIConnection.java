package server.network;

import common.login.RemoteLoginManager;
import server.GameManager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ServerRMIConnection {

    public ServerRMIConnection(GameManager gameManager){
        String ip = "localhost";
        int port = 1099;
        try {
            System.out.print(">>>");
            RemoteLoginManager loginManager = new RMILoginManager(gameManager);
            System.out.print("Starting RMI server...\n>>>");
            Naming.rebind("rmi://" + ip + ":" + port + "/RMILoginManager", loginManager);
            System.out.print("RMI Server on\n>>>");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
