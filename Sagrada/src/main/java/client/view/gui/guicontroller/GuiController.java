package client.view.gui.guicontroller;

import client.view.gui.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiController {

    BorderPane rootLayout;
    GuiPhase phase;

    public GuiController(BorderPane rootLayout){
        this.rootLayout=rootLayout;
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("resources/fxml/Login.fxml"));
        try {
            AnchorPane pane= loader.load();
            rootLayout.setCenter(pane);
            this.phase=loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
