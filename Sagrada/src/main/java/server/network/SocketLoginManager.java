package server.network;

import server.GameManager;
import server.model.Model;
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
    private final Object lock;


    public SocketLoginManager(Socket s, GameManager gameManager, Object lock) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(s.getOutputStream());
        this.in = new ObjectInputStream(s.getInputStream());
        this.gameManager = gameManager;
        this.lock = lock;
    }


    @Override
    public void run() {
        synchronized (lock) {
            try {
                out.writeObject(new String("You are connected to the server!"));
                String name = (String) in.readObject();
                boolean singlePlayer = (Boolean) in.readObject();
                Model model = gameManager.setModel(name, singlePlayer);
                Player player = model.addPlayer(name, model.getState().getPlayers().size());
                ViewProxy viewProxy = new SocketViewProxy(out, model, player);
                new Thread(new ServerSocketHandler(in, viewProxy)).start();
                model.addViewProxyPlayer(viewProxy, player);
                gameManager.startGame(singlePlayer);
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
}
