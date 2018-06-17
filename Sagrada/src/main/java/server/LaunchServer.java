package server;

import common.login.RemoteLoginManager;
import server.network.RMILoginManager;
import server.network.ServerSocketConnection;
import server.network.SocketLoginManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LaunchServer {

    public static final Object lock = new Object();
    private static boolean stopSignal=false;

    public static void main(String[] args) throws Exception{
        GameManager gameManager = new GameManager();
        startRMIServer(gameManager);
        new Thread(() -> startSocketServer(gameManager)).start();
    }

    private static void startRMIServer(GameManager gameManager) {
        String ip = "localhost";
        int port = 1099;
        try {
            System.out.print(">>>");
            RemoteLoginManager loginManager = new RMILoginManager(gameManager);
            System.out.print("Starting RMI server...\n>>>");
            Naming.rebind("rmi://" + ip + ":" + port + "/RMILoginManager", loginManager);
            System.out.print("RMI Server on\n>>>");
        } catch (RemoteException e) {
            System.out.println("Working just on Sockets, retry!");
        } catch (MalformedURLException e) {
            System.err.println("Malformed url.");
        }
    }

    private static void startSocketServer(GameManager gameManager){
        int port = 8010;
        ExecutorService pool = Executors.newCachedThreadPool();
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.print("Listening on " + port + "\n>>>");
            while (!stopSignal) {
                Socket clientSocket = serverSocket.accept();
                pool.submit(() -> new SocketLoginManager().handleConnection(clientSocket, gameManager));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
