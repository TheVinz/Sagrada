package client.view.gui.guicontroller;

import client.view.gui.util.Util;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class WindowFrameChoiceController {

    ViewController viewController;
    GridPane[] frames = new GridPane[4];

    @FXML
    private GridPane grid;
    @FXML
    private VBox cardBox;


    public void setChoice(String[] reps, int[] tokens){
        VBox[] boxes= new VBox[reps.length];
        for(int i=0; i<boxes.length; i++){
            int row=i/2;
            int col=row%2;
            final int x=i;
            HBox tokensBox = new HBox(2);
            tokensBox.setMaxWidth(270);
            tokensBox.setAlignment(Pos.CENTER_RIGHT);
            for(int j=0; j<tokens[i]; j++){
                Circle token = new Circle(5, Color.WHITE);
                tokensBox.getChildren().add(token);
            }
            Button button = new Button("pick");
            button.setOnAction((event) -> {
                viewController.notifyChoice(x);
                Label label = new Label("Waiting for other players");
                label.setStyle("-fx-background-color: rgba(0,0,0,0.4); -fx-text-fill: white; -fx-font-size: 32");
                label.setAlignment(Pos.CENTER);
                viewController.show(label);

            });
            button.setStyle("-fx-background-color: black; -fx-text-fill: white");
            boxes[i]=new VBox();
            boxes[i].setSpacing(10);
            boxes[i].minHeight(300);
            GridPane frame = Util.getWindowFrame(reps[i]);
            frames[i]=frame;
            VBox frameChoice = new VBox(5);
            frameChoice.setAlignment(Pos.TOP_CENTER);
            frameChoice.getChildren().addAll(frame, tokensBox);
            frameChoice.setStyle("-fx-background-color: black; -fx-padding: 10px;");
            frameChoice.setMaxWidth(frame.getWidth()+10);
            boxes[i].getChildren().addAll(frameChoice, button);
            boxes[i].setAlignment(Pos.CENTER);
        }
        grid.add(boxes[0], 0, 0);
        grid.add(boxes[1], 0, 1);
        grid.add(boxes[2], 1, 0);
        grid.add(boxes[3], 1, 1);
    }

    public void setPrivateObjectiveCard(char color){
        ImageView card = Util.getPrivateObjectiveCard(color);
        cardBox.getChildren().add(card);
    }

    public void addListener(ViewController viewController) {
        this.viewController=viewController;
    }

}
