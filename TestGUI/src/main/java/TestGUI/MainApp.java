package TestGUI;

import java.io.IOException;

import TestGUI.client.view.ModelProxy;
import TestGUI.server.Controller;
import TestGUI.server.ViewProxy;
import TestGUI.server.model.Model;
import TestGUI.client.view.PlayerViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private PlayerViewController viewController;

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        rootLayout.getStylesheets().add("File:resources/css/stylesheet.css");

        showPersonOverview();

        Model model=new Model();
        Controller controller= new Controller(model);
        ViewProxy viewProxy=new ViewProxy(model, controller);
        model.addObserver(viewProxy);

        ModelProxy modelProxy=new ModelProxy(viewController);
        viewProxy.bindClientView(modelProxy);
        viewController.bindController(viewProxy);

  /*      primaryStage.setMaximized(true);*/

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("client/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("client/view/PlayerView.fxml"));
            AnchorPane playerView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(playerView);

            viewController = loader.getController();
            viewController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setCursor(Cursor cursor) {
        primaryStage.getScene().setCursor(cursor);
    }
}
