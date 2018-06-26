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
 * The <tt>TaglierinaManualeChoicePhase</tt> handle the user's input in case of request,
 * by the server, of the number of dice moves, thanks to the "Taglierina Manuale" card
 * effect, the user would like to perform.
 * So it just overrides the method handle choice by the {@link GamePhase} class, which
 * shows a popup window with the "one" or "two" choices.
 */
public class TaglierinaManualeChoicePhase extends GamePhase {

    private Stage dialog = null;

    /**
     * Initializes the <tt>TaglierinaManualeChoicePhase</tt> by setting the {@link RemoteController}
     * it will send the {@link GameCommand}s to, and the {@link GameController} of the current game graphical
     * interface.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public TaglierinaManualeChoicePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
    }

    /**
     * Initializes and shows the choice popup.
     * @return a new {@link GamePhase} for waiting server response.
     *
     * @see Response
     */
    @Override
    public GamePhase handleChoice(){
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setAlwaysOnTop(true);
        Platform.setImplicitExit(false);
        dialog.setOnCloseRequest(Event::consume);
        dialog.setTitle("Taglierina Manuale");
        VBox box = new VBox(10);
        HBox buttons = new HBox(10);
        Button oneMove = new Button("One");

        oneMove.setOnAction((event -> {
            dialog.close();
            try {
                controller.command(new GameCommand(Response.CHOICE, Response.TAGLIERINA_MANUALE_ONE_MOVE));
            } catch (IOException e) {
                exceptionRoutine();
            }
        }));

        Button twoMoves = new Button("Two");

        twoMoves.setOnAction((event) -> {
            dialog.close();
            try {
                controller.command(new GameCommand(Response.CHOICE, Response.TAGLIERINA_MANUALE_TWO_MOVES));
            } catch (IOException e) {
                exceptionRoutine();
            }
        });

        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(oneMove, twoMoves);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(new Text("How many dices do you want to move?"), buttons);
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
     * In case of connection drops, if the choice popup is still open, this method
     * closes it.
     */
    @Override
    public void close(){
        if(dialog != null)
            dialog.close();
    }
}
