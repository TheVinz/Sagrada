package client.view.gui.guicontroller;

import client.view.gui.MainApp;
import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class LoginController {

    private ViewController listener;
    private RemoteView model;
    private RemoteController remoteController;
    private String name;
    private BorderPane rootLayout;

    @FXML
    private TextField textField;

    @FXML
    private void rmiLogin(){
        name=textField.getText();
        textField.setText(null);
        try {
            Registry reg = LocateRegistry.getRegistry();
            RemoteLoginManager login =(RemoteLoginManager) reg.lookup("LoginManager");
            //RemoteLoginManager login =(RemoteLoginManager) Naming.lookup("rmi://192.168.1.66:1099/LoginManager");
            remoteController=login.connect(name, model);
            listener.notifyLogin(remoteController);
        }
        catch (NotBoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void socketLogin(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Coming soon");
        alert.showAndWait();
    }

    public void setModel(RemoteView model){
        this.model=model;
    }

    public RemoteController getRemoteController(){
        return this.remoteController;
    }

    public void addListener(ViewController viewController) {
        this.listener=viewController;
    }

    public void setRootLayout(BorderPane rootLayout){
        this.rootLayout=rootLayout;
    }

}
