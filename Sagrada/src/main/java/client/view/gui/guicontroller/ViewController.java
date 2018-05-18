package client.view.gui.guicontroller;

import client.view.gui.MainApp;
import client.view.gui.guimodel.GuiModel;
import client.view.gui.util.Util;
import common.RemoteMVC.RemoteController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ViewController {

    private RemoteController remoteController;

    @FXML
    private BorderPane rootLayout;

    public void init(GuiModel model) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("resources/fxml/Login.fxml"));
        try {
            AnchorPane pane= loader.load();
            rootLayout.setCenter(pane);
            LoginController login = loader.getController();
            login.setModel(model);
            login.addListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyLogin(RemoteController remoteController){
        this.remoteController=remoteController;
    }

    public void loadWindowFrameChoice(String[] reps, int[] tokens){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("resources/fxml/WindowFrameChoice.fxml"));
        try {
            AnchorPane pane = loader.load();
            rootLayout.setCenter(pane);
            WindowFrameChoiceController choiceController = loader.getController();
            choiceController.setChoice(reps, tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
