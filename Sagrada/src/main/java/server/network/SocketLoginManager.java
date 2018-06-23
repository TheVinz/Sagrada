package server.network;

import common.command.GameCommand;
import server.GameManager;
import server.LaunchServer;
import server.model.Model;
import server.model.state.player.Player;
import server.viewproxy.SocketViewProxy;
import server.viewproxy.ViewProxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketLoginManager{

    public void handleConnection(Socket s, GameManager gameManager) {

        String name = "unnamed";

            try {
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                SocketViewProxy viewProxy;
                out.writeObject("You are connected to the server!");
                name = (String) in.readObject();
                System.out.print(name + " connected\n>>>");
                boolean singlePlayer = (Boolean) in.readObject();
                viewProxy = new SocketViewProxy(out, s);
                gameManager.addPlayer(name, viewProxy, singlePlayer);
                viewProxy.mainLoop(in);
            } catch (IOException e) {
                System.out.print("Connection error.\n>>>");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.print(">>>");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print(">>>");
            } finally {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

}
