package client.view.gui.guicontroller;

import client.view.gui.util.Util;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WindowFrameChoiceController {

    @FXML
    private GridPane grid;

    public void setChoice(String[] reps, int[] tokens){
        VBox[] boxes= new VBox[reps.length];
        for(int i=0; i<boxes.length; i++){
            int row=i/2;
            int col=row%2;
            Label label = new Label("Tokens: "+tokens[i]);
            label.setStyle("-fx-font: 22 bold; -fx-background-color: rgba(255,255,255,0.5)");
            Button button = new Button("pick");
            button.setStyle("-fx-background-color: black; -fx-text-fill: white");
            boxes[i]=new VBox();
            boxes[i].setSpacing(10);
            boxes[i].minHeight(300);
            boxes[i].getChildren().addAll(label, Util.getWindowFrame(reps[i]), button);
            boxes[i].setAlignment(Pos.CENTER);
        }
        grid.add(boxes[0], 0, 0);
        grid.add(boxes[1], 0, 1);
        grid.add(boxes[2], 1, 0);
        grid.add(boxes[3], 1, 1);
    }

}
