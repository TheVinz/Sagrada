package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * The <tt>PinzaSgrossatriceChoicePhase</tt> handle the user's input in case of request,
 * by the server, of which effect should "Pinza Sgrossatrice" card perform.
 * So it just overrides the method handle choice by the {@link GamePhase} class, which
 * shows a popup window with the two buttons representing the "increase" and "decrease"
 * options.
 */
public class PinzaSgrossatriceChoicePhase extends GamePhase {

    private Stage dialog;

    /**
     * Initializes the <tt>PinzaSgrossatriceChoicePhase</tt> by setting the {@link RemoteController}
     * it will send the {@link GameCommand}s to, and the {@link GameController} of the current game graphical
     * interface.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public PinzaSgrossatriceChoicePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
    }

    /**
     * Initializes and shows the choices popup.
     * @return a new {@link GamePhase} for waiting server {@link Response}.
     */
    @Override
    public GamePhase handleChoice(){
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setAlwaysOnTop(true);
        Platform.setImplicitExit(false);
        dialog.setOnCloseRequest(Event::consume);
        VBox box = new VBox(10);
        HBox buttons = new HBox(10);
        Button increase = new Button("Increase");

        increase.setOnAction((event) -> {
            try {
                controller.command(new GameCommand(Response.CHOICE, Response.PINZA_SGROSSATRICE_INCREASE));
            } catch (IOException e) {
                exceptionRoutine();
                dialog.close();
                return;
            }
            dialog.close();
        });

        Button decrease = new Button("Decrease");

        decrease.setOnAction((event) -> {
            try {
                controller.command(new GameCommand(Response.CHOICE, Response.PINZA_SGROSSATRICE_DECREASE));
            } catch (IOException e) {
                exceptionRoutine();
                dialog.close();
                return;
            }
            dialog.close();
        });

        buttons.getChildren().addAll(increase, decrease);
        buttons.setAlignment(Pos.CENTER);
        box.getChildren().addAll(new Text("Select a choice"), buttons);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 300, 200);
        dialog.setScene(scene);
        dialog.show();
        return new GamePhase(controller, gameController);
    }

    private void exceptionRoutine() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Connection error");
        gameController.suspend();
    }

    /**
     * In case of connection drops, if the popup is till open, this method closes it.
     */
    @Override
    public void close(){
        if(dialog != null)
            dialog.close();
    }
}
