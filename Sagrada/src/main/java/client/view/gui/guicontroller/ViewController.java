package client.view.gui.guicontroller;

import client.view.gui.guicontroller.gamephase.DiluentePerPastaSaldaChoicePhase;
import client.view.gui.MainApp;
import client.view.gui.guicontroller.gamephase.*;
import client.view.gui.guimodel.GuiModel;
import client.view.gui.util.Util;
import common.response.Response;
import common.RemoteMVC.RemoteController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.rmi.RemoteException;


public class ViewController {

    private RemoteController remoteController;
    private GameController gameController;
    private AnchorPane gamePane;
    private AnchorPane windowFrameChoicesPane;
    private WindowFrameChoiceController windowFrameChoiceController;
    private int id;
    private GamePhase currentPhase;

    @FXML
    private BorderPane rootLayout;

    public void init(GuiModel model) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("resources/fxml/Login.fxml"));
        try {
            AnchorPane pane= loader.load();
            rootLayout.setCenter(pane);
            LoginController login = loader.getController();
            Image background = new Image(MainApp.class.getResource("resources/style/background.jpg").toString());
            BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(0,0,false,false,false,true));
            rootLayout.setBackground(new Background(backgroundImage));
            login.setModel(model);
            login.addListener(this);
            loader=new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("resources/fxml/Game.fxml"));
            gamePane=loader.load();
            gameController=loader.getController();
            gameController.log("Waiting for other players...\n");
            gameController.addListener(this);
            loader=new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("resources/fxml/WindowFrameChoice.fxml"));
            windowFrameChoicesPane = loader.load();
            windowFrameChoiceController = loader.getController();
            windowFrameChoiceController.addListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyLogin(RemoteController remoteController){
        this.remoteController=remoteController;
        Label label = new Label("Waiting server...");
        label.setStyle("-fx-font-size: 40; -fx-text-fill: white; -fx-background-color: black");
        rootLayout.setCenter(label);
    }

    public void loadWindowFrameChoice(String[] reps, int[] tokens){
        Image background = new Image(MainApp.class.getResource("resources/style/gamebackground.jpg").toString());
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(0,0,false,false,false,true));
        rootLayout.setBackground(new Background(backgroundImage));
        rootLayout.setCenter(windowFrameChoicesPane);
        windowFrameChoiceController.setChoice(reps, tokens);
    }

    public void startGame(){
        rootLayout.setCenter(gamePane);
    }

    public void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens, int id) {
        this.id=id;
        for(int i=0; i<names.length; i++){
            if(ids[i]==id)
                gameController.setActiveFrame(names[i], ids[i], windowFrameReps[i], windowFrameFavorTokens[i]);
            else {
                gameController.loadPlayer(names[i], ids[i], windowFrameReps[i], windowFrameFavorTokens[i]);
            }
        }
    }

    public void notifyChoice(int index) {
        try {
            remoteController.command(Response.CHOICE, index);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        startGame();
    }

    public void loadToolCard(int[] toolCards) {
        for(int i=0; i<toolCards.length; i++){
            gameController.loadToolCard(toolCards[i], i);
        }
    }

    public void loadPrivateObjectiveCard(char color) {
        windowFrameChoiceController.setPrivateObjectiveCard(color);
        gameController.setPrivateObjectiveCard(color);
    }

    public void setDraftPool(int[] values, char[] colors) {
        gameController.cleanDraftPool();
        for(int i=0; i<values.length; i++)
            gameController.setDraftPoolDice(i, values[i], colors[i]);
    }

    public void setPublicObjectiveCards(int[] cards) {
        gameController.setPublicObjectiveCards(cards);
    }

    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3) {
        ImageView source;
        String message = gameController.getPlayerName(player) + " moved a dice from ";
        if(sourceType==Response.DRAFT_POOL_CELL) {
            source = gameController.getFromDraftPool(param1);
            message = message + "draft pool to";
        }
        else return;
        if(destType == Response.WINDOW_FRAME_CELL) {
            gameController.setFromWindowFrame(player, param2, param3, source);
            message = message + "window frame.\n";
        }
        else if(destType == Response.ROUND_TRACK_CELL){
            gameController.setFromRoundTrack(param2, param3, source);
        }
        else return;
        gameController.log(message);
    }

    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3, int param4) {
        ImageView source;
        String message = gameController.getPlayerName(player) + " moved a dice from ";
        if(sourceType == Response.WINDOW_FRAME_CELL){
            source = gameController.getFromWindowFrame(player, param1, param2);
            message = message + "his window frame\n";
        }
        else return;
        if(destType == Response.WINDOW_FRAME_CELL){
            gameController.setFromWindowFrame(player, param3, param4, source);
        }
        else return;
        gameController.log(message);
    }


    public void toolCardUsed(int player, int index, int tokens) {
        String message;
        if(player == id) {
            message = "You ";
        }
        else
            message = gameController.getPlayerName(player) + " ";
        message = message + "used "+gameController.getToolCardName(index) +  "\n";
        gameController.log(message);
        gameController.decreaseFavorTokens(player, tokens);
    }

    public void notifyDiceDraw(int player, char color) {
        if(this.id==player)
            DiluentePerPastaSaldaChoicePhase.color=color;
        else{
            String message = gameController.getPlayerName(player) + " drawed a ";
            switch(color){
                case 'b':
                    message = message + "blue ";
                    break;
                case 'r':
                    message = message + "red ";
                    break;
                case 'y':
                    message = message + "yellow ";
                    break;
                case 'p':
                    message = message + "purple ";
                    break;
                case 'g':
                    message = message + "green ";
                    break;
                default:
                    return;
            }
            message = message + "dice\n";
            gameController.log(message);
        }
    }

    public void updateRoundTrack(int round, int[] values, char[] colors) {
        gameController.addRoundTrackBox(round, values, colors);
        gameController.setRound(round);
    }

    public void updateCell(int player, Response type, int index, int value, char color) {
        String message;
        if(type == Response.DRAFT_POOL_CELL){
            message = gameController.getPlayerName(player) + " modified a dice in the draft pool\n";
            gameController.updateDraftPool(index, value, color);
        }
    }

    public void updateCell(int player, Response type, int param1, int param2, int value, char color) {
    }

    public synchronized void startTurn(int id) {
        if(id==this.id) {
            gameController.log("Is your turn!!\n");
            GamePhase.diceMoved=false;
            GamePhase.toolCardUsed=false;
            this.currentPhase=new MainPhase(remoteController, gameController);
        }
        else {
            gameController.log("Is " + gameController.getPlayerName(id) + " turn!!\n");
            currentPhase= new GamePhase(remoteController, gameController);
        }
    }

    public synchronized void handleResponse(Response response) {
        switch(response){
            case DRAFT_POOL_MOVE:
                currentPhase=new MovingDraftPoolPhase(remoteController, gameController);
                break;
            case WINDOW_FRAME_MOVE:
                currentPhase=new MovingWindowFramePhase(remoteController, gameController);
                break;
            case DRAFT_POOL_CELL:
                currentPhase=new DraftPoolPhase(remoteController, gameController);
                break;
            case WINDOW_FRAME:
                currentPhase=new WindowFramePhase(remoteController, gameController);
                break;
            case ROUND_TRACK_CELL:
                currentPhase=new RoundTrackPhase(remoteController, gameController);
                break;
            case PINZA_SGROSSATRICE_CHOICE:
                currentPhase = new PinzaSgrossatriceChoicePhase(remoteController, gameController);
                currentPhase = currentPhase.handleChoice();
                break;
            case TAGLIERINA_MANUALE_CHOICE:
                currentPhase = new TaglierinaManualeChoicePhase(remoteController, gameController);
                currentPhase = currentPhase.handleChoice();
                break;
            case DILUENTE_PER_PASTA_SALDA_CHOICE:
                currentPhase = new DiluentePerPastaSaldaChoicePhase(remoteController, gameController);
                currentPhase = currentPhase.handleChoice();
                break;
            case SUCCESS_MOVE_DONE:
                GamePhase.diceMoved=true;
                currentPhase = new MainPhase(remoteController, gameController);
                break;
            case SUCCESS_USED_TOOL_CARD:
                GamePhase.toolCardUsed=true;
                currentPhase = new MainPhase(remoteController, gameController);
                break;
            case SUCCESS_TOOL_CARD_WITH_MOVE:
                GamePhase.toolCardUsed = true;
                GamePhase.diceMoved = true;
                currentPhase = new MainPhase(remoteController, gameController);
                break;
            case SUSPENDED:
                endTurn();
                Label label= new Label("Disconnected");
                Button button = new Button("Reconnect");
                button.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-size: 32");
                label.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-font-size: 32");
                button.setOnAction((event) -> {
                    try {
                        remoteController.command(Response.ACTIVE_AGAIN);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
                VBox box = new VBox(50);
                box.setAlignment(Pos.CENTER);
                box.getChildren().addAll(label, button);
                rootLayout.setCenter(box);
                break;

            default:
                currentPhase=new MainPhase(remoteController, gameController);
                break;
        }
    }

    public synchronized void roundTrackClick(int round, int index) {
        currentPhase=currentPhase.handleRoundTrack(round, index);
    }

    public synchronized void toolCardClick(int index) {
        currentPhase=currentPhase.handleToolCard(index);
    }

    public synchronized void draftPoolClick(int index){
        currentPhase=currentPhase.handleDraftPool(index);
    }

    public synchronized void windowFrameClick(int row, int col){
        currentPhase=currentPhase.handleWindowFrame(row,col);
    }


    public synchronized void endTurn() {
        try {
            remoteController.command(Response.END_TURN);
            gameController.unableAll();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentPhase=new GamePhase(remoteController, gameController);
    }

    public void endGame(char[] privateObjectiveCards, int[] ids, int[][] points){
        HBox endGameBox = new HBox(20);
        endGameBox.setAlignment(Pos.CENTER);
        for(int i=0; i<points.length; i++) {
            VBox playerBox = new VBox(20);
            playerBox.setAlignment(Pos.CENTER);
            Label nameLabel=new Label(gameController.getPlayerName(ids[i]));
            nameLabel.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-font-size: 26");
            int publicSum=points[i][0]+points[i][1]+points[i][2];
            Label publicPoints = new Label("Public objective cards: "+publicSum);
            Label privatePoints = new Label("Private objective card: " + points[i][3]);
            Label favorPoints = new Label("Favor tokens bonus: " + points[i][4]);
            Label emptyMalus = new Label("Empty cells malus: " + points[i][5]);
            Label total = new Label("Total: " + points[i][6]);
            String style = "-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-font-size: 22";
            publicPoints.setStyle(style);
            privatePoints.setStyle(style);
            favorPoints.setStyle(style);
            emptyMalus.setStyle(style);
            total.setStyle(style);
            GridPane playerFrame = gameController.getPlayerFrame(i);
            ImageView card = Util.getPrivateObjectiveCard(privateObjectiveCards[i]);
            VBox labels= new VBox(5);
            labels.getChildren().addAll(publicPoints, privatePoints, favorPoints, emptyMalus, total);
            playerBox.getChildren().addAll(nameLabel, card, playerFrame, labels);
            endGameBox.getChildren().add(playerBox);
        }
        rootLayout.setCenter(endGameBox);
    }

    public void debug(String message) {
        gameController.log(message);
    }

    public synchronized void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.setTitle("Error");
        alert.showAndWait();
        currentPhase = new MainPhase(remoteController, gameController);
    }
}
