package server.viewproxy;

import common.Notification;
import common.command.GameCommand;
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
    private Player player;

    public SocketViewProxy(ObjectOutputStream out, Model model, Player player) throws RemoteException {
        super(model, player);
        this.out = out;
        this.player = player;
        change(new LoadId(player.getId()));
        new Thread(this::ping).start();
    }

    @Override
    void change(Changement changement) {
        sendData(changement);
    }

    @Override
    void notify(Notification notification) {
        sendData(notification);
    }

    @Override
    void send(Response response) {
        sendData(response);
    }

    @Override
    public void ping() {
        while(ping) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            sendData(null);
        }
    }

    private void sendData(Object data){
        try {
            if(!player.isSuspended()) out.writeObject(data);
        } catch (IOException e) {
            System.out.print(player.getName()+" disconnected.\n>>>");
            super.suspendPlayer();
        }
    }

    public void mainLoop(ObjectInputStream in) throws IOException,ClassNotFoundException {
        do {
            try {
                command((GameCommand) in.readObject());
            } catch (IOException e) {
                super.suspendPlayer();
                throw e;
            }
        } while (true);
    }


}
