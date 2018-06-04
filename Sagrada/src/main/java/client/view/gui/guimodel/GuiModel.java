package client.view.gui.guimodel;

import common.Changer;
import client.view.gui.guicontroller.ViewController;
import common.Notification;
import common.RemoteMVC.RemoteView;
import common.response.Response;
import common.viewchangement.Changement;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GuiModel extends UnicastRemoteObject implements RemoteView {

    private ViewController view;
    private int id;
    private final Changer changer;

    public GuiModel(ViewController view) throws RemoteException {
        super();
        this.view=view;
        changer = new GuiChanger(view);
    }

    @Override
    public void change(Changement changement){
        changement.change(changer);
    }

    @Override
    public void send(Response response) {
        Platform.runLater(() -> view.handleResponse(response));
    }

    @Override
    public void notify(Notification notification){
        if(notification.equals(Notification.WRONG_PARAMETER)){
            Platform.runLater(() -> view.debug(notification.getMessage()));
        }else{
            Platform.runLater(() -> view.error(notification.getMessage()));
        }
    }
}
