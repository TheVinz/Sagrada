package server;

import common.login.RemoteLoginManager;
import server.network.RMILoginManager;
import server.network.ServerSocketConnection;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class LaunchServer {

    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception{
        GameManager gameManager = new GameManager();
        startRMIServer(gameManager);
        ServerSocketConnection serverSocketConnection = new ServerSocketConnection(gameManager, lock);
        new Thread(serverSocketConnection).start();
    }

    private static void startRMIServer(GameManager gameManager) {
        String ip = "localhost";
        int port = 1099;
        try {
            System.out.print(">>>");
            RemoteLoginManager loginManager = new RMILoginManager(gameManager, lock);
            System.out.print("Starting RMI server...\n>>>");
            Naming.rebind("rmi://" + ip + ":" + port + "/RMILoginManager", loginManager);
            System.out.print("RMI Server on\n>>>");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Working just on Sockets, retry!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
