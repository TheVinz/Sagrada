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

/**
 * The <tt>GuiModel</tt> class represents the model for the GUI client MVC. This class receives
 * {@link Changement}s, {@link Response}s and {@link Notification}s from the server and then
 * notifies the {@link ViewController}. This class can either be used with both RMI and Socket connections
 * so it does not know which of them is being used by the player.
 */
public class GuiModel extends UnicastRemoteObject implements RemoteView {

    private ViewController view;
    private final Changer changer;
    private Response lastResponse;

    /**
     * Initialize the <tt>GuiModel</tt> setting the {@link ViewController} and creating a new {@link GuiChanger}.
     * @param view the main controller of the GUI.
     * @throws RemoteException from the {@link UnicastRemoteObject} superclass constructor.
     */
    public GuiModel(ViewController view) throws RemoteException {
        super();
        this.view=view;
        changer = new GuiChanger(view);
    }

    /**
     * Calls method change from the {@link Changer} passing the received {@link Changement}
     * as argument, so the Changer can process it and update the GUI game representation.
     * @param changement the Changement received from the server.
     */
    @Override
    public void change(Changement changement){
        changement.change(changer);
    }

    /**
     * Informs the {@link ViewController} about the {@link Response} received from the server.
     * @param response the Response received from the server.
     */
    @Override
    public void send(Response response) {
        lastResponse=response;
        Platform.runLater(() -> view.handleResponse(response));
    }

    /**
     * Informs the ViewController about the {@link Notification} received from the server.
     * If the Notification is a wrong parameter notification it sends to the ViewController the last
     * response received from the server.
     * @param notification the Notification received from the server.
     */
    @Override
    public void notify(Notification notification){
        if(notification.getType()==Notification.WRONG_PARAMETER){
            Platform.runLater(() -> {
                view.wrongParameter(notification.getMessage());
                view.handleResponse(lastResponse);
            });
        }else{
            Platform.runLater(() -> view.error(notification.getMessage()));
        }
    }

    /**
     * This method is called from the server to check if the client is still connected to the game.
     * @throws RemoteException in case of connection drops.
     */
    @Override
    public void ping() throws RemoteException {}
}
