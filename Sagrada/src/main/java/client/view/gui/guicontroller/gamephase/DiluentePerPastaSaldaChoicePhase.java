package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import client.view.gui.util.Util;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.rmi.RemoteException;

public class DiluentePerPastaSaldaChoicePhase extends GamePhase{

    public static char color;

    public DiluentePerPastaSaldaChoicePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
    }

    @Override
    public GamePhase handleChoice(){
        final Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setAlwaysOnTop(true);
        Platform.setImplicitExit(false);
        dialog.setOnCloseRequest(Event::consume);
        dialog.setTitle("Diluente Per Pasta Salda");
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox box = new VBox(10);
        HBox buttons = new HBox(10);
        box.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        initButtons(buttons, dialog);
        box.getChildren().addAll(new Text("Select dice Value"), buttons);
        Scene scene= new Scene(box, 400, 300);
        dialog.setScene(scene);
        dialog.show();
        return new GamePhase(controller, gameController);
    }

    private void initButtons(HBox buttons, Stage stage) {
        for(int i=0; i<6; i++){
            int index = i+1;
            ImageView image = Util.getImage(color,index);
            Pane button = new Pane();
            button.getChildren().add(image);
            button.getStyleClass().addAll("cell","clickable");
            button.setOnMouseClicked((event) -> {
                try {
                    controller.command(new GameCommand(Response.CHOICE, index));
                    stage.close();
                } catch (RemoteException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Connection error.");
                    alert.showAndWait();
                }
            });
            buttons.getChildren().add(button);
        }
    }
}
