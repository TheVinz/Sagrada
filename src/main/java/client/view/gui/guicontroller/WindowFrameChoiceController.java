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

/**
 * The <tt>WindowFrameChoiceController</tt> in the controller associated to the window frame
 * choice screen. This class set the center of the main {@link javafx.scene.layout.BorderPane}
 * to an {@link javafx.scene.layout.AnchorPane} containing a {@link GridPane} with the four
 * window frame schemes sent by the server, by which the player can choose the one he wants to
 * play with, and a {@link VBox} with the private objective cards associated to the player,
 * one in case of multi-player game or two in case of single-player. The client's {@link ViewController}
 * is updated when the choice is made. The AnchorPane is loaded by the FXML file
 * WindowFrameChoice.fxml in the fxml resource directory.
 */
public class WindowFrameChoiceController {

    private ViewController viewController;
    private GridPane[] frames = new GridPane[4];

    @FXML
    private GridPane grid;
    @FXML
    private VBox cardBox;

    /**
     * Initializes and shows the {@link GridPane} containing the four schemes the player can choose.
     * @param reps the array of scheme representations.
     * @param tokens the array of favor tokens associated to the corresponding scheme.
     */
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

    /**
     * Adds an {@link ImageView} with the private objective card images to the cards {@link VBox}.
     * @param color the color of the private objective card.
     */
    public void setPrivateObjectiveCard(char color){
        ImageView card = Util.getPrivateObjectiveCard(color);
        cardBox.getChildren().add(card);
    }

    /**
     * Sets the {@link ViewController} of the GUI client.
     * @param viewController the ViewController to be notified after the player has
     *                       selected his scheme.
     */
    public void addListener(ViewController viewController) {
        this.viewController=viewController;
    }

}
