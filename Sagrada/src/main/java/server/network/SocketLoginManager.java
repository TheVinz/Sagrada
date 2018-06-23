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
            out.writeObject("You are connected to the server!");
            name = (String) in.readObject();
            System.out.print(name + " connected\n>>>");
            boolean singlePlayer = (Boolean) in.readObject();
            viewProxy = new SocketViewProxy(out, s);
            gameManager.addPlayer(name, viewProxy, singlePlayer);
            viewProxy.mainLoop(in);
        } catch (RemoteException e) {
            System.out.print(name + " connection error.\n>>>");
        } catch (IOException e) {
            System.out.print(name + " connection error.\n>>>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.print(">>>");
        } catch (Exception e) {
            //TODO inserire codice in caso di riconnessioni con username o singleplayer invalido
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                System.err.print("Error on Socket.close()\n>>>");
            }
        }
    }

}
