package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
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
import java.rmi.RemoteException;


public class TaglierinaManualeChoicePhase extends GamePhase {
    public TaglierinaManualeChoicePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
    }

    @Override
    public GamePhase handleChoice(){
        final Stage dialog = new Stage();
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
}
