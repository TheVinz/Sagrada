package client.view.gui.guicontroller;

import client.view.gui.guicontroller.gamephase.DiluentePerPastaSaldaChoicePhase;
import client.view.gui.MainApp;
import client.view.gui.guicontroller.gamephase.*;
import client.view.gui.guimodel.GuiModel;
import client.view.gui.util.Util;
import common.command.GameCommand;
import common.response.Response;
import common.RemoteMVC.RemoteController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;



public class ViewController {

    private RemoteController remoteController;
    private GameController gameController;
    private LoginController loginController;

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
            loginController = loader.getController();
            Image background = new Image(MainApp.class.getResource("resources/style/background.jpg").toString());
            BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(0,0,false,false,false,true));
            rootLayout.setBackground(new Background(backgroundImage));
            loginController.setModel(model);
            loginController.addListener(this);
            loader=new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("resources/fxml/WindowFrameChoice.fxml"));
            windowFrameChoicesPane = loader.load();
            windowFrameChoiceController = loader.getController();
            windowFrameChoiceController.addListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void choseDifficulty(){
        final Stage dialog = new Stage();
        dialog.setTitle("Difficulty Choice");
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setAlwaysOnTop(true);
        Platform.setImplicitExit(false);
        dialog.setOnCloseRequest(Event::consume);
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogBox = new VBox(10);
        dialogBox.setStyle("-fx-background-color: rgba(255,255,255,0.4)");
        dialogBox.setAlignment(Pos.CENTER);
        Label title = new Label("Difficulty: ");
        title.setStyle("-fx-font: 22 bold;");
        dialogBox.getChildren().add(title);
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        for(int i=5; i>0; i--){
            int difficulty = i;
            Pane button = Util.getDifficultyButton(difficulty);
            button.setOnMouseClicked((event) -> {
                try {
                    remoteController.command(new GameCommand(Response.CHOICE, difficulty));
                } catch (IOException e) {
                    handleIOException();
                }
                dialog.close();
            });
            buttonsBox.getChildren().add(button);
        }
        dialogBox.getChildren().add(buttonsBox);
        Scene scene = new Scene(dialogBox,700, 150);
        scene.getStylesheets().add(rootLayout.getScene().getStylesheets().get(0));
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    public void notifyLogin(RemoteController remoteController, boolean singleplayer){
        this.remoteController=remoteController;
        Label label = new Label("Waiting server...");
        label.setStyle("-fx-font-size: 40; -fx-text-fill: white; -fx-background-color: black");
        rootLayout.setCenter(label);
        FXMLLoader loader=new FXMLLoader();
        if(!singleplayer)
            loader.setLocation(MainApp.class.getResource("resources/fxml/Game.fxml"));
        else
            loader.setLocation(MainApp.class.getResource("resources/fxml/SinglePlayer.fxml"));
        try {
            gamePane=loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameController=loader.getController();
        if(!singleplayer)
            gameController.log("Waiting for other players...\n");
        gameController.addListener(this);
    }

    public void notifyPlayerDisconnected(int id){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Disconnected");
        alert.setHeaderText(gameController.getPlayerName(id) + " disconnected.");
        alert.showAndWait();
    }
    public void notifyPlayerReconnected(int id){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reconnected");
        alert.setHeaderText(gameController.getPlayerName(id) + " reconnected.");
        alert.showAndWait();
    }

    public void loadWindowFrameChoice(String[] reps, int[] tokens){
        setGameBackground();
        rootLayout.setCenter(windowFrameChoicesPane);
        windowFrameChoiceController.setChoice(reps, tokens);
    }

    public void clear(){
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("resources/fxml/Game.fxml"));
        try {
            gamePane=loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameController=loader.getController();
        gameController.addListener(this);
    }

    public void setGameBackground(){
        Image background = new Image(MainApp.class.getResource("resources/style/gamebackground.jpg").toString());
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(0,0,false,false,false,true));
        rootLayout.setBackground(new Background(backgroundImage));
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
            remoteController.command(new GameCommand(Response.CHOICE, index));
        } catch (IOException e) {
            handleIOException();
        }
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
    public void setWindowFrameDices(int id, int[][] values, char[][] colors){
        GridPane frame = gameController.getPlayerFrame(id);
        for(int i=0; i<values.length; i++){
            for(int j=0; j<values[i].length; j++){
                if(values[i][j] >= 1 && values[i][j]<=6) {
                    ImageView dice = Util.getImage(colors[i][j], values[i][j]);
                    gameController.setFromWindowFrame(id, i, j, dice);
                }
            }
        }
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

    public void removeDraftPoolDice(int index){
        gameController.getFromDraftPool(index);
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
        if(loginController.isSingleplayer())
            gameController.removeToolCard(index);
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
                try {
                    currentPhase = currentPhase.handleChoice();
                } catch (IOException e) {
                    handleIOException();
                }
                break;
            case TAGLIERINA_MANUALE_CHOICE:
                currentPhase = new TaglierinaManualeChoicePhase(remoteController, gameController);
                try {
                    currentPhase = currentPhase.handleChoice();
                } catch (IOException e) {
                    handleIOException();
                }
                break;
            case DILUENTE_PER_PASTA_SALDA_CHOICE:
                currentPhase = new DiluentePerPastaSaldaChoicePhase(remoteController, gameController);
                try {
                    currentPhase = currentPhase.handleChoice();
                } catch (IOException e) {
                    handleIOException();
                }
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
                handleIOException();
                break;

            default:
                currentPhase=new MainPhase(remoteController, gameController);
                break;
        }
    }

    public void handleIOException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("You have been disconnected");
        alert.showAndWait();
        try {
            String name = gameController.getPlayerName(this.id);
            init(new GuiModel(this));
            loginController.setName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void roundTrackClick(int round, int index) {
        try {
            currentPhase=currentPhase.handleRoundTrack(round, index);
        } catch (IOException e) {
            handleIOException();
        }
    }

    public synchronized void toolCardClick(int index) {
        try {
            currentPhase=currentPhase.handleToolCard(index);
        } catch (IOException e) {
            handleIOException();
        }
    }

    public synchronized void draftPoolClick(int index){
        try {
            currentPhase=currentPhase.handleDraftPool(index);
        } catch (IOException e) {
            handleIOException();
        }
    }

    public synchronized void windowFrameClick(int row, int col){
        try {
            currentPhase=currentPhase.handleWindowFrame(row,col);
        } catch (IOException e) {
            handleIOException();
        }
    }


    public synchronized void endTurn() {
        try {
            remoteController.command(new GameCommand(Response.END_TURN));
            gameController.unableAll();
        } catch (IOException e) {
            handleIOException();
        }
        currentPhase=new GamePhase(remoteController, gameController);
    }

    public void notifyPrivateObjectiveCardChoice(char card1, char card2){
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        Pane first = new Pane();
        first.getStyleClass().add("end_game_card");
        first.getChildren().add(Util.getPrivateObjectiveCardEndGame(card1));
        first.setOnMouseClicked((mouseEvent -> {
            try {
                remoteController.command(new GameCommand(Response.CHOICE,0));
            } catch (IOException e) {
                handleIOException();
            }
        }));
        Pane second = new Pane();
        second.setPrefHeight(400);
        second.setPrefWidth(272);
        second.getStyleClass().add("end_game_card");
        second.getChildren().add(Util.getPrivateObjectiveCardEndGame(card2));
        second.setOnMouseClicked((mouseEvent -> {
            try {
                remoteController.command(new GameCommand(Response.CHOICE,1));
            } catch (IOException e) {
                handleIOException();
            }
        }));
        box.getChildren().addAll(first, second);
        rootLayout.setCenter(box);
    }

    public synchronized void endSinglePlayerGame(char card, int[] points, int targetPoints){



        String style = "-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-font-size: 26";

        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER);

        VBox frameBox = new VBox(10);
        frameBox.setAlignment(Pos.CENTER);
        Label targetPointsLabel = new Label("Target points: " + targetPoints);
        targetPointsLabel.setStyle(style);
        ImageView privateCard = Util.getPrivateObjectiveCardEndGame(card);
        frameBox.getChildren().addAll(privateCard, gameController.getPlayerFrame(this.id), targetPointsLabel);

        VBox pointsBox = new VBox(10);
        pointsBox.setAlignment(Pos.CENTER);

        int publicPoints = points[0]+points[1];
        Label publicCardPoints = new Label("Public objective cards points: " + publicPoints);
        Label privateCardPoints = new Label("Private objective card points: " + points[2]);
        Label emptyCellMalus = new Label("Empty cells malus: " + points[3]);
        Label total = new Label("Total: " + points[4]);

        publicCardPoints.setStyle(style);
        privateCardPoints.setStyle(style);
        emptyCellMalus.setStyle(style);
        total.setStyle(style);

        String result;

        if(points[4]>targetPoints)
            result = "You win!";
        else
            result="You lose";

        Label resultLabel = new Label(result);
        resultLabel.setStyle("-fx-background-color: rgba(255, 165, 0, 0.9); -fx-text-fill: black; -fx-font-size: 28");

        pointsBox.getChildren().addAll(publicCardPoints, privateCardPoints, emptyCellMalus, total, resultLabel);

        box.getChildren().addAll(frameBox, pointsBox);

        Button rematchButton = new Button("Play again");
        rematchButton.setOnMouseClicked((mouseEvent -> {
            try {
                init(new GuiModel(this));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Connection error.");
                alert.showAndWait();
                System.exit(-1);
            }
            rootLayout.setTop(null);
        }));
        Button exitButton = new Button("Exit");
        exitButton.setOnMouseClicked((mouseEvent -> System.exit(0)));
        style = "-fx-background-color: orange; -fx-font-size: 24";
        exitButton.setStyle(style);
        rematchButton.setStyle(style);

        VBox buttonsBox = new VBox(40);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(rematchButton, exitButton);

        box.getChildren().add(buttonsBox);

        rootLayout.setCenter(box);
    }

    public synchronized void endGame(char[] privateObjectiveCards, int[] ids, int[][] points){
        HBox endGameBox = new HBox(20);
        endGameBox.setAlignment(Pos.CENTER);
        for(int i=0; i<points.length; i++) {
            VBox playerBox = new VBox(20);
            playerBox.setAlignment(Pos.CENTER);
            Label nameLabel=new Label(gameController.getPlayerName(ids[i]));
            nameLabel.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-font-size: 26");
            int publicSum=points[ids[i]][0]+points[ids[i]][1]+points[ids[i]][2];
            Label publicPoints = new Label("Public objective cards: "+publicSum);
            Label privatePoints = new Label("Private objective card: " + points[ids[i]][3]);
            Label favorPoints = new Label("Favor tokens bonus: " + points[ids[i]][4]);
            Label emptyMalus = new Label("Empty cells malus: " + points[ids[i]][5]);
            Label total = new Label("Total: " + points[ids[i]][6]);
            String style = "-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-font-size: 22";
            publicPoints.setStyle(style);
            privatePoints.setStyle(style);
            favorPoints.setStyle(style);
            emptyMalus.setStyle(style);
            total.setStyle(style);
            GridPane playerFrame = gameController.getPlayerFrame(ids[i]);
            ImageView card = Util.getPrivateObjectiveCard(privateObjectiveCards[ids[i]]);
            VBox labels= new VBox(5);
            labels.getChildren().addAll(publicPoints, privatePoints, favorPoints, emptyMalus, total);
            playerBox.getChildren().addAll(nameLabel, card, playerFrame, labels);
            if(ids[i] == this.id){
                playerBox.setStyle("-fx-background-color: radial-gradient(center 50% 0%, radius 70%, rgba(252,176,19,0.3), rgba(0,0,0,0))");
            }
            endGameBox.getChildren().add(playerBox);
        }
        rootLayout.setCenter(endGameBox);
    }

    public synchronized void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.setTitle("Error");
        alert.showAndWait();
        currentPhase = new MainPhase(remoteController, gameController);
    }

    public synchronized void wrongParameter(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.setTitle("Wrong parameter");
        currentPhase = new GamePhase(remoteController, gameController);
        alert.showAndWait();
    }

    public void setFullScreen() {
        ((Stage) rootLayout.getScene().getWindow()).setFullScreen(true);
    }
}
