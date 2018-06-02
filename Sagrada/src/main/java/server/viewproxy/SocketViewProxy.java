package server.viewproxy;

import common.Notification;
import common.response.Response;
import common.viewchangement.Changement;
import server.model.Model;
import server.model.state.player.Player;

import java.rmi.RemoteException;

public class SocketViewProxy extends ViewProxy {

    private Model model;
    private Player player;

    public SocketViewProxy(Model model, Player player) throws RemoteException {
        super(model, player);
        this.model = model;
        this.player = player;
    }
    @Override
    void change(Changement changement) {

    }

    @Override
    void notify(Notification notification) {

    }

    @Override
    void send(Response response) {

    }
}
