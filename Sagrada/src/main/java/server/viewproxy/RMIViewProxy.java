package server.viewproxy;

import common.Notification;
import common.RemoteMVC.RemoteView;
import common.response.Response;
import common.viewchangement.Changement;
import common.viewchangement.LoadId;
import server.model.Model;
import server.model.state.player.Player;
import java.rmi.RemoteException;


public class RMIViewProxy extends ViewProxy {

    private RemoteView remoteView;
    private Player player;


    public RMIViewProxy(Model model, Player player) throws RemoteException {
        super(model, player);
        this.player = player;
        new Thread(this::ping).start();
    }

    synchronized public void bindRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
        change(new LoadId(player.getId()));
    }

    @Override
    void change(Changement changement) {
        try {
            if(!player.isSuspended()) remoteView.change(changement);
        } catch (RemoteException e) {
            super.suspendPlayer();
        }
    }

    @Override
    void notify(Notification notification) {
        try {
            if(!player.isSuspended()) remoteView.notify(notification);
        } catch (RemoteException e) {
            super.suspendPlayer();
        }
    }

    @Override
    void send(Response response) {
        try {
            if(!player.isSuspended()) remoteView.send(response);
        } catch (RemoteException e) {
            super.suspendPlayer();
        }
    }

    @Override
    public void ping(){
        while(ping) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            try {
                remoteView.ping();
            } catch (RemoteException e) {
                super.suspendPlayer();
            }
        }
    }
}