package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class PinzaSgrossatriceChoicePhase extends GamePhase {
    public PinzaSgrossatriceChoicePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
    }

    @Override
    public GamePhase handleChoice(){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox box = new VBox(10);
        HBox buttons = new HBox(10);
        Button increase = new Button("Increase");
        increase.setOnAction((event) -> {
            try {
                controller.command(Response.CHOICE, Response.PINZA_SGROSSATRICE_INCREASE);
            } catch (InvalidMoveException e) {
                gameController.log(e.getMessage()+"\n");
                dialog.close();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            dialog.close();
        });
        Button decrease = new Button("Decrease");
        decrease.setOnAction((event) -> {
            try {
                controller.command(Response.CHOICE, Response.PINZA_SGROSSATRICE_DECREASE);
            } catch (InvalidMoveException e) {
                gameController.log(e.getMessage()+"\n");
                dialog.close();
            } catch (RemoteException e) {
                e.printStackTrace();
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
}
