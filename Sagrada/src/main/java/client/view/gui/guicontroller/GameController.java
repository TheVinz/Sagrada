package client.view.gui.guicontroller;

import client.view.gui.guicontroller.gamephase.GamePhase;
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
    private int[] tokens = new int[4];
    private Label[] labels = new Label[4];
    private GridPane activeFrame;
    private int id;
    private final String draggable = "draggable";
    private final String droppable = "droppable";
    private final String clickable = "clickable";

    private ViewController controller;

    /*Init phase*/
    public void addListener(ViewController controller){
        this.controller=controller;
    }

    public String getPlayerName(int id){
        return playerNames[id];
    }

    public String getToolCardName(int index){
        Node card = toolCardBox.getChildren().get(index);
        return card.getAccessibleText();
    }

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
        tokens[id]=windowFrameFavorToken;
        labels[id]=tokensLabel;
        textArea.appendText(name + " joined the game!!\n");

    }

    public void loadToolCard(int toolCard, final int i) {
        Pane card=Util.getToolCard(toolCard);
        toolCardBox.getChildren().add(card);
        card.setOnMouseClicked((event) -> handleToolCardClick(i));
    }

    public void setPrivateObjectiveCard(char color) {
        VBox box=new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(Util.getPrivateObjectiveCard(color));
        objectiveGrid.add(box, 0, 0);
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

    public void setActiveFrame(GridPane frame, int id) {
        this.id=id;
        frameBox.getChildren().add(frame);
        activeFrame=frame;
        for(Node n : frame.getChildren()){
            n.setOnDragDropped(event -> handleDrop(event, n));
            n.setOnDragOver(event -> handleDragOver(event));
            n.setOnDragDetected((event) -> handleFrameDrag(event, n));
            n.setOnMouseClicked((event) -> handleFrameClick(n));
            n.getStyleClass().add("cell");
        }
    }

    /*==========================================================================================*/
    /*Round routine*/

    public void unableAll(){
        draftPoolBox.getStyleClass().remove(draggable);
        draftPoolBox.getStyleClass().remove(clickable);
        activeFrame.getStyleClass().remove(clickable);
        activeFrame.getStyleClass().remove(draggable);
        activeFrame.getStyleClass().remove(droppable);
        roundTrackBox.getStyleClass().remove(clickable);
        toolCardBox.getStyleClass().remove(clickable);
    }
    public void mainPhase(){
        unableAll();
        if(!GamePhase.diceMoved) {
            draftPoolBox.getStyleClass().add(draggable);
            activeFrame.getStyleClass().add(droppable);
        }
        if(!GamePhase.toolCardUsed)
            toolCardBox.getStyleClass().add(clickable);
        log("What do you want to do?\n");
    }
    public void movingDraftPoolPhase(){
        unableAll();
        draftPoolBox.getStyleClass().add(draggable);
        activeFrame.getStyleClass().add(droppable);
        log("Move a dice from the draft pool to your window frame\n");
    }
    public void movingWindowFrame(){
        unableAll();
        activeFrame.getStyleClass().add(droppable);
        activeFrame.getStyleClass().add(draggable);
        log("Move a dice in your window frame\n");
    }
    public void roundTrackPhase(){
        unableAll();
        roundTrackBox.getStyleClass().add(clickable);
        log("Select a dice from the round track\n");
    }
    public void windowFramePhase(){
        unableAll();
        activeFrame.getStyleClass().add(clickable);
        log("Select a dice from your window frame");
    }
    public void draftPoolPhase(){
        unableAll();
        draftPoolBox.getStyleClass().add(clickable);
        log("Select a dice from the draft pool");
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
            int index=i;
            pane = new Pane();
            image = Util.getImage(colors[i], values[i]);
            image.setX(2);
            image.setY(2);
            pane.getChildren().add(0, image);
            pane.getStyleClass().add("cell");
            box.getChildren().add(i, pane);
            pane.setOnMouseClicked((event) -> handleRoundTrackClick(round, index));
        }
    }

    public void setDraftPoolDice(final int i, int value, char color) {
        ImageView dice = Util.getImage(color, value);
        Pane pane = new Pane();
        dice.setX(2);
        dice.setY(2);
        pane.getStyleClass().add("cell");
        pane.getChildren().add(dice);
        draftPoolBox.getChildren().add(i, pane);
        pane.setOnDragDetected((event) -> handleStartDrag(event, i));
        pane.setOnMouseClicked((event) -> draftPoolCkick(event, i));
    }

    public synchronized void log(String message){
        textArea.appendText(message);
    }
    /*======================================================================================*/
    /*Modifiers*/

    public void decreaseFavorTokens(int id, int tokens){
        this.tokens[id]=this.tokens[id]-tokens;
        labels[id].setText("Favor tokens : "+this.tokens[id]);
    }

    public void updateDraftPool(int index, int value, char color) {
        Pane cell = (Pane) draftPoolBox.getChildren().get(index);
        ImageView image = Util.getImage(color, value);
        cell.getChildren().clear();
        if(!cell.getStyleClass().contains("cell"))
            cell.getStyleClass().add("cell");
        cell.getChildren().add(0, image);
    }

    public ImageView getFromDraftPool(int index) {
        Pane pane = (Pane) draftPoolBox.getChildren().get(index);
        ImageView image = (ImageView) (pane).getChildren().get(0);
        pane.getChildren().clear();
        pane.getStyleClass().remove("cell");
        return image;
    }

    public ImageView getFromWindowFrame(int player, int param1, int param2) {
        GridPane frame = frames[player];
        Pane pane = null;
        for (Node n : frame.getChildren())
            if(GridPane.getColumnIndex(n)==param2 && GridPane.getRowIndex(n)==param1)
                pane = (Pane) n;
        ImageView image = (ImageView) pane.getChildren().get(0);
        int index = param1*5 + param2;
        char emptyImage = reps[player].charAt(index);
        pane.getChildren().clear();
        pane.getChildren().add(Util.getImage(emptyImage));
        if(player == id){
            frame = activeFrame;
            for (Node n : frame.getChildren())
                if(GridPane.getColumnIndex(n)==param2 && GridPane.getRowIndex(n)==param1)
                    pane = (Pane) n;
            pane.getChildren().clear();
            pane.getChildren().add(Util.getImage(emptyImage));
        }
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

    public void setFromRoundTrack(int round, int index, ImageView source) {
        ImageView image = new ImageView(source.getImage());
        image.setY(2);
        image.setX(2);
        image.setFitHeight(50);
        image.setFitWidth(50);
        HBox box = (HBox) roundTrackBox.getChildren().get(round-1);
        Pane pane = (Pane) box.getChildren().get(index);
        pane.getChildren().set(0, image);
    }

    /*===========================================================================================================*/
    /*Event handler*/

    private void handleToolCardClick(int i) {
        if(toolCardBox.getStyleClass().contains(clickable))
            controller.toolCardClick(i);
    }

    private void draftPoolCkick(MouseEvent event, int index) {
        if(draftPoolBox.getStyleClass().contains(clickable)){
            controller.draftPoolClick(index);
        }
    }

    private void handleStartDrag(MouseEvent event, int index) {
        Pane source = (Pane) event.getSource();
        ImageView image = (ImageView) source.getChildren().get(0);
        if(draftPoolBox.getStyleClass().contains(draggable)) {
            controller.draftPoolClick(index);
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(image.getImage());
            db.setContent(content);
            event.consume();
        }
    }
    private void handleFrameDrag(MouseEvent event, Node n) {
        ImageView image = (ImageView) ((Pane) n).getChildren().get(0);
        if(activeFrame.getStyleClass().contains(draggable)) {
            controller.windowFrameClick(GridPane.getRowIndex(n), GridPane.getColumnIndex(n));
            Dragboard db = n.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(image.getImage());
            db.setContent(content);
            event.consume();
        }
    }

    private void handleDragOver(DragEvent event) {
        Node target = (Node) event.getTarget();
        if(activeFrame.getStyleClass().contains(droppable)) {
            if (event.getGestureSource() != target && event.getDragboard().hasImage())
                event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        }
    }

    private void handleDrop(DragEvent event, Node n) {
        if(activeFrame.getStyleClass().contains(droppable)) {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                controller.windowFrameClick(GridPane.getRowIndex(n), GridPane.getColumnIndex(n));
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        }
    }

    private void handleFrameClick(Node n) {
        if(activeFrame.getStyleClass().contains(clickable)) {
            controller.windowFrameClick(GridPane.getRowIndex(n), GridPane.getColumnIndex(n));
        }
    }


    private void handleRoundTrackClick(int round, int index) {
        if(roundTrackBox.getStyleClass().contains(clickable)){
            controller.roundTrackClick(round, index);
        }
    }


    @FXML
    private void endTurn(){
        controller.endTurn();
    }

}
