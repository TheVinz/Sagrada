package server.network;

import server.GameManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketConnection implements Runnable{
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private final int port = 8010;
    private GameManager gameManager;
    private boolean stopSignal = false;

    public ServerSocketConnection(GameManager gameManager)  throws IOException {
        this.gameManager = gameManager;
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        System.out.println(">>> Listening on " + port);
    }

    public void run()  {
        stopSignal=false;
        while (!stopSignal) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println(">>> New connection " + clientSocket.getRemoteSocketAddress());
                pool.submit(new SocketLoginManager(clientSocket, gameManager));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws IOException {
        stopSignal=true;
        serverSocket.close();
        pool.shutdown();
    }
}
