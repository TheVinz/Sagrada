package client.view.gui.guicontroller;

import client.view.gui.util.Util;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameController {
    @FXML
    private HBox draftPoolBox;
    @FXML
    private GridPane framesGrid;
    @FXML
    private GridPane objectiveGrid;
    @FXML
    private HBox toolCardBox;
    @FXML
    private VBox frameBox;
    @FXML
    private TextArea textArea;

    GridPane[] frames = new GridPane[4];
    String[] playerNames = new String[4];

    private ViewController controller;

    public void loadPlayer(String name, int id, GridPane windowFrame, int windowFrameFavorToken) {
        int row=id/2;
        int col=id%2;
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-font-size: 26");
        Label tokensLabel = new Label("Favor Tokens: "+windowFrameFavorToken);
        tokensLabel.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-font-size: 22");
        box.getChildren().addAll(nameLabel, tokensLabel, windowFrame);
        framesGrid.add(box, col, row);
        frames[id]=windowFrame;
        playerNames[id]=name;
        textArea.appendText(name + " joined the game!!\n");

    }

    public void loadToolCard(int toolCard, final int i) {
        ImageView card=Util.getToolCard(toolCard);
        card.setOnMouseClicked((event) -> controller.notifyToolCardClicked(i));
        toolCardBox.getChildren().add(card);
    }

    public void setPrivateObjectiveCard(char color) {
        VBox box=new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(Util.getPrivateObjectiveCard(color));
        objectiveGrid.add(box, 0, 0);
    }

    public void setDraftPoolDice(final int i, int value, char color) {
        ImageView dice = Util.getImage(color, value);
        draftPoolBox.getChildren().add(i, dice);
        dice.setOnMouseClicked((event) -> controller.draftPoolClick(i));
    }

    public void setPublicObjectiveCards(int[] cards) {
        for(int i=0; i<cards.length; i++) {
            VBox box=new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(Util.getPublicObjectiveCard(cards[i]));
            int col=(i+1)%2;
            int row=(i+1)/2;
            objectiveGrid.add(box, col, row);
        }
    }

    public void log(String message){
        textArea.appendText(message);
    }

    public void setActiveFrame(GridPane frame) {
        frameBox.getChildren().add(frame);
        for(Node n : frame.getChildren()){
            n.setOnMouseClicked((event -> controller.windowFrameClick(GridPane.getRowIndex(n), GridPane.getColumnIndex(n))));
        }
    }

    public ImageView getFromDraftPool(int index) {
        return (ImageView) draftPoolBox.getChildren().get(index);
    }

    public ImageView getFromWindowFrame(int id, int row, int col) {
        GridPane pane=frames[id];
        for(Node n : pane.getChildren())
            if(GridPane.getColumnIndex(n) == col && GridPane.getRowIndex(n)==row)
                return (ImageView) ((Pane) n).getChildren().get(0);
        return null;
    }

    public void setFromWindowFrame(int id, int row, int col, ImageView source) {
        GridPane pane = frames[id];
        if(pane==null) return;
        else pane.add(source, col, row);
    }

    public String getPlayerName(int id){
        return playerNames[id];
    }

    public void addListener(ViewController viewController) {
        this.controller=viewController;
    }
}
