package server.viewproxy;

import common.Notification;
import common.RemoteMVC.RemoteView;
import common.response.Response;
import common.viewchangement.Changement;
import common.viewchangement.LoadId;
import server.model.Model;
import server.model.state.player.Player;
import java.rmi.RemoteException;


public class RMIViewProxy extends ViewProxy{

    private RemoteView remoteView;
    private Model model;
    private Player player;


    public RMIViewProxy(Model model, Player player) throws RemoteException{
        super(model, player);
        this.model = model;
        this.player = player;
    }

    synchronized public void bindRemoteView(RemoteView remoteView){
        this.remoteView=remoteView;
        change(new LoadId(player.getId()));
    }
    
    @Override
    void change(Changement changement) {
        try {
            remoteView.change(changement);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    void notify(Notification notification) {
        try {
            remoteView.notify(notification);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    void send(Response response) {
        try {
            remoteView.send(response);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }




}
