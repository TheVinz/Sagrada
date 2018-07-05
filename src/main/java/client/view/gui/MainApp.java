package client.view.gui;

import client.view.gui.guicontroller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    private ViewController controller;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private URL resource;

    /**
     * Launches the JavaFX application.
     * @param args the command line's arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and shown the root layout.
     * @param primaryStage the primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Sagrada");
        primaryStage.setOnCloseRequest((event) -> System.exit(0));
        initRootLayout();
        controller.init();
    }

    private void initRootLayout() {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(resource);
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
