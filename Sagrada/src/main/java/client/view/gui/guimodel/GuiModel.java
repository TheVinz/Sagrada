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
import java.util.concurrent.locks.Lock;

public class GuiModel extends UnicastRemoteObject implements RemoteView {

    private ViewController view;
    private int id;
    private final Changer changer;
    private Response lastResponse;

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
        System.out.println("Response");
        lastResponse=response;
        Platform.runLater(() -> view.handleResponse(response));
    }

    @Override
    public void notify(Notification notification){
        System.out.println("Notification");
        if(notification.getType()==Notification.WRONG_PARAMETER){
            Platform.runLater(() -> {
                view.wrongParameter(notification.getMessage());
                view.handleResponse(lastResponse);
            });
        }else{
            Platform.runLater(() -> view.error(notification.getMessage()));
        }
    }
}
