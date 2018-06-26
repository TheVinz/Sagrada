package client.view.gui.guicontroller;

import client.network.ClientConnection;
import client.view.gui.guimodel.GuiModel;
import common.RemoteMVC.RemoteController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class LoginController extends ClientConnection {

    private ViewController listener;
    private boolean singleplayer=false;

    private String ip = "localhost";


    @FXML
    private TextField textField;

    @FXML
    private void rmiLogin(){
        String name=textField.getText();
        textField.setText(null);
        try {
            super.connectRmi(ip, name, new GuiModel(listener), singleplayer);
        }
        catch (NotBoundException e) {
            rmiError();
        } catch (RemoteException e) {
            rmiError();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void rmiError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("RMI server is not working, start rmiregistry and try again.");
        alert.showAndWait();
    }

    @FXML
    private void socketLogin(){
        String name = textField.getText();
        textField.setText(null);
        try {
            super.connectSocket(ip, new GuiModel(listener) , name, singleplayer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setSinglePlayer(){
        singleplayer=true;
    }

    @FXML
    private void unsetSinglePlayer(){
        singleplayer=false;
    }

    public boolean isSingleplayer(){
        return this.singleplayer;
    }


    public void addListener(ViewController viewController) {
        this.listener=viewController;
    }

    public void setName(String name){
        textField.setText(name);
    }

    @Override
    public void setRemoteController(RemoteController remoteController) {
        Platform.runLater(() -> listener.notifyLogin(remoteController, singleplayer));
    }

    @Override
    public void notifyDisconnection() {
        Platform.runLater(() -> listener.handleIOException());
    }

    @Override
    public void connectionError(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Your connection has been rejected.");
            alert.setContentText("Try again with a different username or game mode.");
            alert.showAndWait();
        });
    }
}
