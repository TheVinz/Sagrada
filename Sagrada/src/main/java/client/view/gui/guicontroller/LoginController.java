package client.view.gui.guicontroller;

import client.view.gui.guimodel.GuiModel;
import client.network.ClientSocketHandler;
import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.login.RemoteLoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;



public class LoginController {

    private ViewController listener;
    private RemoteView model;
    private RemoteController remoteController;
    private String name;
    private boolean singleplayer=false;

    private String ip ="localhost";
    private int port = 1099;
    private int socketPort = 8010;

    @FXML
    private TextField textField;

    @FXML
    private void rmiLogin(){
        name=textField.getText();
        textField.setText(null);
        try {
            RemoteLoginManager login =(RemoteLoginManager) Naming.lookup("rmi://"+ip+":"+port+"/RMILoginManager");
            remoteController=login.connect(name, model, singleplayer);
            listener.notifyLogin(remoteController, singleplayer);
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
        Socket connection;
        ObjectOutputStream out;
        ObjectInputStream in;
        name = textField.getText();
        textField.setText(null);
        try{
            connection = new Socket(ip, socketPort);
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
            System.out.println((String) in.readObject());
            out.writeObject(name);
            out.writeObject(new Boolean(singleplayer));
            ClientSocketHandler clientSocketHandler = new ClientSocketHandler(in, out,new GuiModel(listener));
            new Thread(clientSocketHandler).start();
            listener.notifyLogin(clientSocketHandler, singleplayer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    public void setModel(RemoteView model){
        this.model=model;
    }


    public void addListener(ViewController viewController) {
        this.listener=viewController;
    }

}
