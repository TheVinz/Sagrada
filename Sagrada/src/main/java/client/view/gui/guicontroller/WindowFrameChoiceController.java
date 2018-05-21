package client.view.gui.guicontroller;

import client.view.gui.util.Util;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



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
            Label label = new Label("Tokens: "+tokens[i]);
            label.setStyle("-fx-font: 22 bold; -fx-background-color: rgba(255,255,255,0.5)");
            Button button = new Button("pick");
            button.setOnAction((event) -> viewController.notifyChoice(x));
            button.setStyle("-fx-background-color: black; -fx-text-fill: white");
            boxes[i]=new VBox();
            boxes[i].setSpacing(10);
            boxes[i].minHeight(300);
            GridPane frame = Util.getWindowFrame(reps[i]);
            frames[i]=frame;
            boxes[i].getChildren().addAll(label, frame, button);
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

    public GridPane getFrame(int index){
        return frames[index];
    }
}
