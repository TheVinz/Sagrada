package server.viewproxy;

import common.Notification;
import common.response.Response;
import common.viewchangement.Changement;
import common.viewchangement.LoadId;
import server.model.Model;
import server.model.state.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

public class SocketViewProxy extends ViewProxy {

    private final ObjectOutputStream out;
    private Model model;
    private Player player;

    public SocketViewProxy(ObjectOutputStream out, Model model, Player player) throws RemoteException {
        super(model, player);
        this.out = out;
        this.model = model;
        this.player = player;
        change(new LoadId(player.getId()));
    }
    @Override
    void change(Changement changement) {
        try {
            out.writeObject(changement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void notify(Notification notification) {
        try {
            out.writeObject(notification);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void send(Response response) {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sent");
    }


}
