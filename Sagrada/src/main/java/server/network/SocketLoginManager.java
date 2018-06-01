package server.network;

import server.GameManager;
import server.model.state.player.Player;
import server.viewproxy.SocketViewProxy;
import server.viewproxy.ViewProxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketLoginManager implements Runnable{
    private Socket socket;
    private final ObjectInputStream in;
    private GameManager gameManager;
    private final ObjectOutputStream out;


    public SocketLoginManager(Socket s, GameManager gameManager) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(s.getOutputStream());
        this.in = new ObjectInputStream(s.getInputStream());
        this.gameManager = gameManager;
    }


    @Override
    synchronized public void run() {
        try {
            out.writeObject(new String("You are connected to the server!"));
            String name = (String) in.readObject();
            boolean singlePlayer = (Boolean) in.readObject();
            gameManager.setModel(singlePlayer);
            Player player = gameManager.getModel().addPlayer(name, gameManager.getModel().getState().getPlayers().size());
            ViewProxy viewProxy = new SocketViewProxy(out, gameManager.getModel(), player);
            new Thread(new ServerSocketHandler(in, viewProxy)).start();
            gameManager.getModel().addViewProxyPlayer(viewProxy, player);
            System.out.print(name + " connected\n>>>");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
