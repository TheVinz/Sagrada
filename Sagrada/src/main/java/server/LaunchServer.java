package server;

import common.login.RemoteLoginManager;
import server.network.RMILoginManager;
import server.network.ServerRMIConnection;
import server.network.ServerSocketConnection;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class LaunchServer {
    public static void main(String[] args) throws Exception{
        GameManager gameManager = new GameManager();
        ServerRMIConnection serverRMIConnection = new ServerRMIConnection(gameManager);
        ServerSocketConnection serverSocketConnection = new ServerSocketConnection(gameManager);
        new Thread(serverSocketConnection).start();
        gameManager.init();
    }
}
