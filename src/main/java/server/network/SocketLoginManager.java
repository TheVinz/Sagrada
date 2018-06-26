package server.network;

import server.GameManager;
import server.viewproxy.SocketViewProxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketLoginManager{

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
                e.printStackTrace();
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
