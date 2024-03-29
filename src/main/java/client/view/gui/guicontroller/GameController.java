package client.view.gui.guicontroller;

import client.view.gui.guicontroller.gamephase.GamePhase;
import client.view.gui.util.Util;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.rmi.server.UnicastRemoteObject;


/**
 * The <tt>GameController</tt> class is the class that controls all the graphic related
 * to the playing phase either for single-player and multi-player games. The FXML files
 * associated to this controller (Game.fxml for multi-player and SinglePlayer.fxml for
 * single-player, both in fxml resource directory) are composed by:
 * <ul>
 *     <li>an {@link HBox} for draft pool's dices;</li>
 *     <li>a {@link VBox} showing the player's window frame;</li>
 *     <li>an HBox for other players frames (not present in single-player games);</li>
 *     <li>an HBox for tool card image {@link ImageView}s;</li>
 *     <li>an HBox for both private and public objective card image ImageViews;</li>
 *     <li>an HBox for the round track images and dices;</li>
 *     <li>a {@link Label} for showing some text.</li>
 * </ul>
 */
public class GameController {
    @FXML
    private HBox draftPoolBox;
    @FXML
    private HBox framesBox;
    @FXML
    private HBox objectiveCardsBox;
    @FXML
    private HBox toolCardsBox;
    @FXML
    private VBox frameBox;
    @FXML
    private Label textLabel;
    @FXML
    private HBox roundTrack;

    private boolean canEnd = true;

    private GridPane[] frames = new GridPane[4];
    private String[] reps = new String[4];
    private String[] playerNames = new String[4];
    private HBox[] tokens = new HBox[4];
    private boolean[][] hasDice = new boolean[4][5];
    private VBox[] roundTrackBoxes = new VBox[10];
    private GridPane activeFrame;
    private int id;
    private static final String draggable = "draggable";
    private static final String droppable = "droppable";
    private static final String clickable = "clickable";

    private ViewController controller;


    void addListener(ViewController controller){
        this.controller=controller;
    }

    String getPlayerName(int id){
        return playerNames[id];
    }
    GridPane getPlayerFrame(int id){
        return frames[id];
    }
    String getToolCardName(int index){
        Node card = toolCardsBox.getChildren().get(index);
        return card.getAccessibleText();
    }

    void loadPlayer(String name, int id, String rep, int windowFrameFavorToken) {
        GridPane windowFrame = Util.getWindowFrame(rep);
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7f);
        windowFrame.setEffect(reflection);
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-background-color: rgba(0,0,0,0.4); -fx-font-size: 18; -fx-text-fill: white");
        HBox tokens = new HBox(5);
        tokens.setAlignment(Pos.CENTER_RIGHT);
        tokens.setMaxWidth(windowFrame.getWidth());
        for(int i=0; i<windowFrameFavorToken; i++){
            Circle token = new Circle(7, Color.WHITE);
            tokens.getChildren().add(token);
        }
        tokens.setStyle("-fx-background-color: rgba(0,0,0,0.4)");
        box.getChildren().addAll(nameLabel, tokens, windowFrame);
        framesBox.getChildren().add(box);
        frames[id]=windowFrame;
        reps[id]= rep;
        playerNames[id]=name;
        this.tokens[id]=tokens;
    }

    void loadToolCard(int toolCard, final int i) {
        Pane card=Util.getToolCard(toolCard);
        toolCardsBox.getChildren().add(card);
        card.setOnMouseClicked((event) -> handleToolCardClick(i));
    }

    void setPrivateObjectiveCard(char color) {
        VBox box=new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(Util.getPrivateObjectiveCard(color));
        objectiveCardsBox.getChildren().add(box);
    }

    void setPublicObjectiveCards(int[] cards) {
        for (int card : cards) {
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(Util.getPublicObjectiveCard(card));
            objectiveCardsBox.getChildren().add(box);
        }
    }

    void setActiveFrame(String name, int id, String rep, int tokens) {
        GridPane frame = Util.getWindowFrame(rep);
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7f);
        frame.setEffect(reflection);
        this.id=id;
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-background-color: rgba(0,0,0,0.4); -fx-font-size: 18; -fx-text-fill: white");
        HBox tokensBox = new HBox(5);
        tokensBox.setAlignment(Pos.CENTER_RIGHT);
        tokensBox.setMaxWidth(frame.getWidth());
        tokensBox.setStyle("-fx-background-color: rgba(0,0,0,0.4)");
        for(int i=0; i<tokens; i++){
            Circle token = new Circle(7, Color.WHITE);
            tokensBox.getChildren().add(token);
        }
        frameBox.getChildren().addAll(nameLabel, tokensBox, frame);
        activeFrame=frame;
        for(Node n : frame.getChildren()){
            n.setOnDragDropped(event -> handleDrop(event, n));
            n.setOnDragOver(event -> handleDragOver(event));
            n.setOnDragDetected((event) -> handleFrameDrag(event, n));
            n.setOnMouseClicked((event) -> handleFrameClick(n));
            n.setOnDragDone((event) -> handleDragDone(event, n));
            n.getStyleClass().add("cell");
        }
        for(boolean[] booleans : hasDice)
            for(boolean b : booleans)
                b=false;
        frames[id]=frame;
        reps[id]= rep;
        playerNames[id]=name;
        this.tokens[id]=tokensBox;
    }

    /*==========================================================================================*/
    /*Round routine*/


    private void unableAll(){
        draftPoolBox.getStyleClass().remove(draggable);
        draftPoolBox.getStyleClass().remove(clickable);
        activeFrame.getStyleClass().remove(clickable);
        activeFrame.getStyleClass().remove(draggable);
        activeFrame.getStyleClass().remove(droppable);
        roundTrack.getStyleClass().remove(clickable);
        toolCardsBox.getStyleClass().remove(clickable);
        canEnd = false;
    }

    /**
     * This method sets the graphic for the {@link GamePhase}:
     * removes all the dragging and clicking hover properties.
     */
    public void gamePhase(){
        unableAll();
    }

    /**
     * This method sets the graphic for the {@link client.view.gui.guicontroller.gamephase.MainPhase}:
     * <ul>
     *     <li>drag from draft pool to window frame;</li>
     *     <li>click on tool cards.</li>
     * </ul>
     */
    public void mainPhase(){
        unableAll();
        if(!GamePhase.isDiceMoved()) {
            draftPoolBox.getStyleClass().add(draggable);
            activeFrame.getStyleClass().add(droppable);
        }
        if(!GamePhase.isToolCardUsed())
            toolCardsBox.getStyleClass().add(clickable);
        log("What do you want to do?");
        canEnd = true;
    }

    /**
     * This method sets the graphic for the {@link client.view.gui.guicontroller.gamephase.MovingDraftPoolPhase}:
     * <ul>
     *     <li>drag from draft pool to window frame.</li>
     * </ul>
     */
    public void movingDraftPoolPhase(){
        unableAll();
        draftPoolBox.getStyleClass().add(draggable);
        activeFrame.getStyleClass().add(droppable);
        log("Move a dice from the draft pool to your window frame\n");
    }

    /**
     * This method sets the graphic for the {@link client.view.gui.guicontroller.gamephase.MovingWindowFramePhase}:
     * <ul>
     *     <li>drag from window frame to window frame.</li>
     * </ul>
     */
    public void movingWindowFrame(){
        unableAll();
        activeFrame.getStyleClass().add(droppable);
        activeFrame.getStyleClass().add(draggable);
        log("Move a dice in your window frame\n");
    }

    /**
     * This method sets the graphic for the {@link client.view.gui.guicontroller.gamephase.RoundTrackPhase}:
     * <ul>
     *     <li>click on round track dices.</li>
     * </ul>
     */
    public void roundTrackPhase(){
        unableAll();
        roundTrack.getStyleClass().add(clickable);
        log("Select a dice from the round track\n");
    }

    /**
     * This method sets the graphic for the {@link client.view.gui.guicontroller.gamephase.WindowFramePhase}:
     * <ul>click on window frame cells.</ul>
     */
    public void windowFramePhase(){
        unableAll();
        activeFrame.getStyleClass().add(clickable);
        log("Select a cell from your window frame\n");
    }

    /**
     * This method sets the graphic for the {@link client.view.gui.guicontroller.gamephase.DraftPoolPhase}:
     * <ul>
     *     <li>click on draft pool dices.</li>
     * </ul>
     */
    public void draftPoolPhase(){
        unableAll();
        draftPoolBox.getStyleClass().add(clickable);
        log("Select a dice from the draft pool\n");
    }

    void cleanDraftPool() {
        draftPoolBox.getChildren().clear();
    }

    void addRoundTrackBox(int round, int[] values, char[] colors) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        box.setSpacing(5);
        box.setPrefWidth(54);
        roundTrackBoxes[round-1]=box;
        Pane pane;
        ImageView image;
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

    void setDraftPoolDice(final int i, int value, char color) {
        ImageView dice = Util.getImage(color, value);
        Pane pane = new Pane();
        if(dice != null) {
            dice.setX(2);
            dice.setY(2);
            pane.getChildren().add(dice);
        }
        else
            pane.getStyleClass().add("empty");
        pane.getStyleClass().add("cell");
        draftPoolBox.getChildren().add(i, pane);
        pane.setOnDragDetected(event -> handleStartDrag(event, i));
        pane.setOnMouseClicked(event -> draftPoolCkick( i));
    }

    synchronized void log(String message){
        textLabel.setText(message);
    }
    /*======================================================================================*/
    /*Modifiers*/

    void decreaseFavorTokens(int id, int tokens){
        HBox tokensBox=this.tokens[id];
        tokensBox.getChildren().remove(0);
        if(tokens==2)
            tokensBox.getChildren().remove(0);
    }

    void updateDraftPool(int index, int value, char color) {
        Pane cell = (Pane) draftPoolBox.getChildren().get(index);
        ImageView image = Util.getImage(color, value);
        cell.getChildren().clear();
        cell.getStyleClass().remove("empty");
        cell.getChildren().add(0, image);
    }

    ImageView getFromDraftPool(int index) {
        Pane pane = (Pane) draftPoolBox.getChildren().get(index);
        ImageView image = (ImageView) (pane).getChildren().get(0);
        pane.getChildren().clear();
        pane.getStyleClass().add("empty");
        return image;
    }

    synchronized ImageView getFromWindowFrame(int player, int row, int col) {
        GridPane frame = frames[player];
        Pane pane = null;
        for (Node n : frame.getChildren())
            if(GridPane.getColumnIndex(n)==col && GridPane.getRowIndex(n)==row)
                pane = (Pane) n;
        ImageView image = (ImageView) pane.getChildren().get(0);
        int index = row*5 + col;
        char emptyImage = reps[player].charAt(index);
        pane.getChildren().clear();
        pane.getChildren().add(Util.getImage(emptyImage));
        if(player == id){
            frame = activeFrame;
            for (Node n : frame.getChildren())
                if(GridPane.getColumnIndex(n)==col && GridPane.getRowIndex(n)==row)
                    pane = (Pane) n;
            pane.getChildren().clear();
            pane.getChildren().add(Util.getImage(emptyImage));
            hasDice[row][col]=false;
        }
        return image;
    }

    synchronized void setFromWindowFrame(int id, int row, int col, ImageView source) {
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
            hasDice[row][col]=true;
        }
    }

    void setFromRoundTrack(int round, int index, ImageView source) {
        ImageView image = new ImageView(source.getImage());
        image.setY(2);
        image.setX(2);
        image.setFitHeight(50);
        image.setFitWidth(50);
        VBox box = roundTrackBoxes[round-1];
        Pane pane = (Pane) box.getChildren().get(index);
        pane.getChildren().set(0, image);
    }

    void removeToolCard(int index){
        toolCardsBox.getChildren().set(index, new Pane());
    }

    /*===========================================================================================================*/
    /*Event handler*/

    private void handleToolCardClick(int i) {
        if(toolCardsBox.getStyleClass().contains(clickable))
            controller.toolCardClick(i);
    }

    private void draftPoolCkick(int index) {
        if(draftPoolBox.getStyleClass().contains(clickable)){
            controller.draftPoolClick(index);
        }
    }

    private void handleStartDrag(MouseEvent event, int index) {
        Pane source = (Pane) event.getSource();
        ImageView image = (ImageView) source.getChildren().get(0);
        if(draftPoolBox.getStyleClass().contains(draggable) && image!=null) {
            controller.draftPoolClick(index);
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(image.getImage());
            db.setContent(content);
            event.consume();
        }
    }
    private void handleFrameDrag(MouseEvent event, Node n) {
        int row = GridPane.getRowIndex(n);
        int col = GridPane.getColumnIndex(n);
        Image image = ((ImageView) ((Pane) n).getChildren().get(0)).getImage();
        if(activeFrame.getStyleClass().contains(draggable) && hasDice[row][col]) {
            Pane pane = (Pane) n;
            int index = row*5 + col;
            char emptyImage = reps[id].charAt(index);
            ((ImageView) pane.getChildren().get(0)).setImage(Util.getImage(emptyImage).getImage());
            Dragboard db = n.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(image);
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
        Dragboard db = event.getDragboard();
        Node source =(Node) event.getGestureSource();
        if(activeFrame.getStyleClass().contains(droppable)) {
            if (db.hasImage()) {
                event.setDropCompleted(true);
                event.consume();
                if(activeFrame.getChildren().contains(source)) {
                    controller.windowFrameClick(GridPane.getRowIndex(source), GridPane.getColumnIndex(source));
                }
                controller.windowFrameClick(GridPane.getRowIndex(n), GridPane.getColumnIndex(n));
            }
        }
    }

    private void handleDragDone(DragEvent event, Node n){
        Dragboard db = event.getDragboard();
        Image image = db.getImage();
        ((ImageView) ((Pane) n).getChildren().get(0)).setImage(image);
    }

    private void handleFrameClick(Node n) {
        if(activeFrame.getStyleClass().contains(clickable)) {
            controller.windowFrameClick(GridPane.getRowIndex(n), GridPane.getColumnIndex(n));
        }
    }


    private void handleRoundTrackClick(int round, int index) {
        if(roundTrack.getStyleClass().contains(clickable)){
            controller.roundTrackClick(round, index);
        }
    }


    public void setRound(int round) {
        if(round == 10)
            return;
        for(Node n : roundTrack.getChildren())
            ((VBox) n).getChildren().get(0).getStyleClass().remove("selected");
        ((VBox) roundTrack.getChildren().get(round)).getChildren().get(0).getStyleClass().add("selected");
    }

    @FXML
    private void endTurn(){
        if(canEnd) {
            controller.endTurn();
        }
    }

    @FXML
    private void showRoundTrack(){
        int max = 0;
        VBox box;
        for(int i=0; i<roundTrackBoxes.length; i++){
            box = (VBox) roundTrack.getChildren().get(i);
            if(roundTrackBoxes[i]!=null) {
                box.getChildren().add(roundTrackBoxes[i]);
                if (roundTrackBoxes[i].getChildren().size() > max)
                    max = roundTrackBoxes[i].getChildren().size();
            }
        }
        roundTrack.setPrefHeight(roundTrack.getHeight() + 55*(max+1));
    }

    @FXML
    private void hideRoundTrack(){
        roundTrack.setPrefHeight(60);
        VBox box;
        for(int i=0; i<roundTrackBoxes.length; i++){
            box = (VBox) roundTrack.getChildren().get(i);
            if(box.getChildren().size()>1)
                box.getChildren().remove(1);
        }
    }

    @FXML
    private void setFullScreen(){
        this.controller.setFullScreen();
    }

    @FXML
    private void close(){
        System.exit(0);
    }

    /**
     * This method is called in case of connection drops, calling the handle IOException
     * routine on the {@link ViewController}.
     */
    public void suspend() {
        controller.handleIOException();
    }
}
