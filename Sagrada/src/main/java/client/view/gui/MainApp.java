package client.view.gui;

import client.view.gui.guicontroller.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.IOException;

public class MainApp extends Application {

    
    Stage primaryStage;
    BorderPane rootLayout;
    GuiController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Sagrada");

        initRootLayout();

        controller=new GuiController(rootLayout);

    }

    private void initRootLayout() {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("resources/fxml/RootLayout.fxml"));
        try {
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
