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
                synchronized (LaunchServer.lock) {
                    out.writeObject(new String("You are connected to the server!"));
                    name = (String) in.readObject();
                    boolean singlePlayer = (Boolean) in.readObject();
                    Model model = gameManager.getModel(name, singlePlayer);
                    Player player = model.addPlayer(name);
                    viewProxy = new SocketViewProxy(out, model, player);
                    model.addViewProxyPlayer(viewProxy, player);
                    gameManager.startGame(model);
                    System.out.print(name + " connected\n>>>");
                }
                viewProxy.mainLoop(in);
                s.close();
            } catch (IOException e) {
                System.out.print(name + " disconnected.\n>>>");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.print(">>>");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print(">>>");
            }
    }

}
