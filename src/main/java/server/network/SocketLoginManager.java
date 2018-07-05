package server.network;

import server.GameManager;
import server.viewproxy.SocketViewProxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * The <tt>SocketLoginManager</tt> is the class used to handle the Socket connections to the server.
 */
public class SocketLoginManager{

    /**
     * Reads the username from the Socket input stream and sends it to the {@link GameManager} to handle the client's connection,
     * then creates a new {@link server.viewproxy.ViewProxy} to start the socket input main loop. If the player cannot log in
     * with the selected username the connection is closed sending an error message to the client.
     * @param s the client's socket.
     * @param gameManager the server's game manager.
     */
    public void handleConnection(Socket s, GameManager gameManager) {

        String name = "unnamed";

        try (ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
            SocketViewProxy viewProxy;
            name = (String) in.readObject();
            Thread.currentThread().setName(name + " - socket connection");
            System.out.print(name + " connected\n>>>");
            boolean singlePlayer = (Boolean) in.readObject();
            viewProxy = new SocketViewProxy(out, s);
            try {
                gameManager.addPlayer(name, viewProxy, singlePlayer);
                viewProxy.mainLoop(in);
            } catch (Exception e) {
                out.writeObject("ERROR");
            }
        } catch (RemoteException e) {
            System.out.print(name + " connection error.\n>>>");
        } catch (IOException e) {
            System.out.print(name + " connection error.\n>>>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.print(">>>");
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                System.err.print("Error on Socket.close()\n>>>");
            }
        }
    }

}
