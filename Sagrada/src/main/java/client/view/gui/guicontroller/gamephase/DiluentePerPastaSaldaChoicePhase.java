package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import client.view.gui.util.Util;
import common.RemoteMVC.RemoteController;
import common.response.Response;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class DiluentePerPastaSaldaChoicePhase extends GamePhase{

    public static char color;

    public DiluentePerPastaSaldaChoicePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
    }

    @Override
    public GamePhase handleChoice(){
        final Stage dialog = new Stage();
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
                stage.close();
                try {
                    controller.command(Response.CHOICE, index);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            buttons.getChildren().add(button);
        }
    }
}
