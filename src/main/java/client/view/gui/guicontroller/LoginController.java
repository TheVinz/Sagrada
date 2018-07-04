package client.view.gui.guicontroller;

import client.settings.Settings;
import client.network.ClientConnection;
import client.view.gui.guimodel.GuiModel;
import common.RemoteMVC.RemoteController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.MalformedURLException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The <tt>LoginController</tt> is the class that performs the login to the server.
 * This class shows a {@link javafx.scene.layout.VBox} containing a {@link TextField},
 * for typing the username the user wants to log with, a {@link javafx.scene.control.RadioButton},
 * to select the game modality (single-player or multi-player), and two {@link javafx.scene.control.Button}s:
 * one for the socket login and one for RMI. In case of successful login this class notifies it to
 * the {@link ViewController}, otherwise it shows an error {@link Alert} informing the user of the
 * causes of the error.
 */
public class LoginController extends ClientConnection {

    private ViewController listener;
    private boolean singleplayer=false;
    private GuiModel model;


    @FXML
    private TextField ipField;


    @FXML
    private TextField textField;

    @FXML
    private void initialize(){
        ipField.setText(Settings.getServerIp());
        textField.setText(Settings.getUsername());
    }

    @FXML
    private void rmiLogin(){
        String ip = this.ipField.getText();
        String name=textField.getText();
        Label label = new Label("Waiting server...");
        label.setStyle("-fx-font-size: 40; -fx-text-fill: white; -fx-background-color: black");
        listener.show(label);
        try{
            super.connectRmi(ip, name, model, singleplayer);
        }
        catch (NotBoundException | RemoteException e) {
            rmiError();
        } catch (MalformedURLException e) {
            connectionError();
        }

    }

    private void rmiError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("RMI server is not working, start rmiregistry and try again.");
        alert.showAndWait();
    }

    @FXML
    private void socketLogin(){
        String ip = this.ipField.getText();
        String name = this.textField.getText();
        Label label = new Label("Waiting server...");
        label.setStyle("-fx-font-size: 40; -fx-text-fill: white; -fx-background-color: black");
        listener.show(label);
        super.connectSocket(ip, model , name, singleplayer);
    }

    @FXML
    private void setSinglePlayer(){
        singleplayer=true;
    }

    @FXML
    private void unsetSinglePlayer(){
        singleplayer=false;
    }

    /**
     * Get method for the game modality.
     * @return true if the player selected single-player mode, false for multi-player.
     */
    public boolean isSingleplayer(){
        return this.singleplayer;
    }

    /**
     * Set the {@link ViewController} this class should notify in case of successful login.
     * @param viewController the ViewController associated to this GUI client.
     */
    void addListener(ViewController viewController) {
        this.listener=viewController;
        try {
            this.model = new GuiModel(listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method unexports the {@link GuiModel} previously created and instantiates a new one.
     * Also stops the server ping thread.
     * @see UnicastRemoteObject
     */
    void closeConnection(){
        ping = false;
        if(this.model != null) {
            try {
                UnicastRemoteObject.unexportObject(model, true);
            } catch (NoSuchObjectException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set a previously chosen username to show on the {@link TextField} instead of
     * the default message. This method is called in case of disconnection from the server.
     * @param name the username previously chosen by the user.
     */
    public void setName(String name){
        textField.setText(name);
    }

    /**
     * Set the {@link RemoteController} to be used during the game for sending messages to the
     * server. This controller will be notified to the ViewController in case of successful login.
     * @param remoteController the RemoteController to be notified to the ViewController.
     */
    @Override
    public void setRemoteController(RemoteController remoteController) {
        Settings.save(ipField.getText(), textField.getText());
        listener.notifyLogin(remoteController, singleplayer);
    }

    /**
     * Notifies to the ViewController that the connection dropped.
     */
    @Override
    public void notifyDisconnection() {
        listener.handleIOException();
    }

    /**
     * Shows the error {@link Alert} informing the user that the username he is trying to
     * connect with is already taken by another player.
     */
    @Override
    public void connectionError(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Your connection has been rejected.");
            alert.setContentText("Try again with a different username.");
            alert.showAndWait();
        });
    }
}

