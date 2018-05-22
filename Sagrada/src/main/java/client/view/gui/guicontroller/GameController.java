package client.view.gui.guicontroller;

import client.view.gui.util.Util;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
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
    @FXML
    private VBox roundTrackBox;

    private GridPane[] frames = new GridPane[4];
    private String[] reps = new String[4];
    private String[] playerNames = new String[4];
    private GridPane activeFrame;
    private int id;

    private ViewController controller;

    public void loadPlayer(String name, int id, String rep, int windowFrameFavorToken) {
        int row=id/2;
        int col=id%2;
        GridPane windowFrame = Util.getWindowFrame(rep);
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
        reps[id]= rep;
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
        Pane pane = new Pane();
        dice.setX(2);
        dice.setY(2);
        pane.getStyleClass().add("cell");
        pane.getChildren().add(dice);
        draftPoolBox.getChildren().add(i, pane);
        dice.setOnDragDetected((event) -> handleStartDrag(event, i));
    }

    private void handleStartDrag(MouseEvent event, int index) {
        controller.draftPoolClick(index);
        ImageView source = (ImageView) event.getSource();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putImage(source.getImage());
        db.setContent(content);
        event.consume();
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

    public void setActiveFrame(GridPane frame, int id) {
        this.id=id;
        frameBox.getChildren().add(frame);
        activeFrame=frame;
        for(Node n : frame.getChildren()){
            n.setOnDragDropped(event -> handleDrop(event, n));
            n.setOnDragOver(event -> handleDragOver(event));
            n.getStyleClass().add("cell");
        }
    }

    private void handleDragOver(DragEvent event) {
        Node target = (Node) event.getTarget();
        if(event.getGestureSource() != target && event.getDragboard().hasImage())
            event.acceptTransferModes(TransferMode.MOVE);
        event.consume();
    }

    private void handleDrop(DragEvent event, Node n) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasImage()) {
            controller.windowFrameClick(GridPane.getRowIndex(n), GridPane.getColumnIndex(n));
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void handleFrameClick(Node n) {
        controller.windowFrameClick(GridPane.getRowIndex(n), GridPane.getColumnIndex(n));
    }

    public ImageView getFromDraftPool(int index) {
        ImageView image = (ImageView) draftPoolBox.getChildren().get(index);
        draftPoolBox.getChildren().set(index,new ImageView());
        return image;
    }

    public void setFromWindowFrame(int id, int row, int col, ImageView source) {
        ImageView image;
        GridPane grid = frames[id];
        Pane target=null;
        for (Node n : grid.getChildren()){
            if(GridPane.getRowIndex(n)==row && GridPane.getColumnIndex(n)==col)
                target= (Pane) n;
        }
        if(target==null) return;
        image = ((ImageView) target.getChildren().get(0));
        image.setImage(source.getImage());
        image.setFitHeight(50);
        image.setFitWidth(50);
        if(id==this.id) {
            for (Node n : activeFrame.getChildren()){
                if(GridPane.getRowIndex(n)==row && GridPane.getColumnIndex(n)==col)
                    target= (Pane) n;
            }
            image = ((ImageView) target.getChildren().get(0));
            image.setImage(source.getImage());
            image.setFitHeight(50);
            image.setFitWidth(50);
        }
    }

    public String getPlayerName(int id){
        return playerNames[id];
    }

    public void addListener(ViewController viewController) {
        this.controller=viewController;
    }

    public void cleanDraftPool() {
        draftPoolBox.getChildren().clear();
    }

    public void addRoundTrackBox(int round, int[] values, char[] colors) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(10);
        box.setPadding(new Insets(0, 10 , 0 , 50));
        box.setMinHeight(75);
        box.setPrefHeight(75);
        Pane pane;
        ImageView image;
        roundTrackBox.getChildren().add(round-1, box);
        for(int i=0; i<values.length; i++) {
            pane = new Pane();
            image = Util.getImage(colors[i], values[i]);
            image.setX(2);
            image.setY(2);
            pane.getChildren().add(0, image);
            box.getChildren().add(i, pane);
        }
    }

    @FXML
    private void endTurn(){
        controller.endTurn();
    }

}
