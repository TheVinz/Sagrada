package server;

import common.login.RemoteLoginManager;
import server.network.RMILoginManager;
import server.network.SocketLoginManager;
import server.settings.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * the <tt>LaunchServer</tt> is the class containing main method for the server.
 */
public class LaunchServer {

    private static String ip;
    private static final int RMI_PORT = 1099;
    private static final int SOCKET_PORT = 8080;

    /**
     * main method for starting server. Creates a new {@link GameManager} and initialized the rmi and the socket connection handlers.
     * @param args the command line args.
     */
    public static void main(String[] args) {
        ip = Settings.getServerIp();
        System.out.print(">>>Server IP : " + ip + "\n>>>");
        GameManager gameManager = new GameManager();
        startRMIServer(gameManager);
        new Thread(() -> startSocketServer(gameManager)).start();
    }

    private static void startRMIServer(GameManager gameManager) {

        System.setProperty("java.rmi.server.hostname", ip);

        try {
            Registry reg = LocateRegistry.createRegistry(RMI_PORT);
            System.out.print(">>>");
            RemoteLoginManager loginManager = new RMILoginManager(gameManager);
            System.out.print("Starting RMI server...\n>>>");
            reg.rebind("RMILoginManager", loginManager);
            System.out.print("RMI Server on\n>>>");
        } catch (RemoteException e) {
            System.out.println("Working just on Sockets, retry!\n" + e.getCause());
        }
    }

    private static void startSocketServer(GameManager gameManager){
        Thread.currentThread().setName("Sagrada - socket connection handler");
        ExecutorService pool = Executors.newCachedThreadPool();
        try(ServerSocket serverSocket = new ServerSocket(SOCKET_PORT)) {
            System.out.print("Listening on " + SOCKET_PORT + "\n>>>");
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                pool.submit(() -> {
                    new SocketLoginManager().handleConnection(clientSocket, gameManager);
                    Thread.currentThread().setName("Thread parked");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
