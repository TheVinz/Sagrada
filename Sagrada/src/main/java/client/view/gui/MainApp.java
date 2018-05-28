package client.view.gui;

import client.view.gui.guicontroller.ViewController;
import client.view.gui.guimodel.GuiModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class MainApp extends Application {

    ViewController controller;
    Stage primaryStage;
    BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Sagrada");

        initRootLayout();

        try {
            GuiModel model = new GuiModel(controller);
            controller.init(model);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    private void initRootLayout() {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("resources/fxml/RootLayout.fxml"));
        try {
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            scene.getStylesheets().add(this.getClass().getResource("resources/style/main.css").toExternalForm());
            primaryStage.show();
            primaryStage.setFullScreen(true);
            controller=loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
