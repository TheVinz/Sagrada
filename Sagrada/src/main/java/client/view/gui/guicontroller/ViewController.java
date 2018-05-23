package client.view.gui.guicontroller;

import client.view.gui.MainApp;
import client.view.gui.guicontroller.gamephase.*;
import client.view.gui.guimodel.GuiModel;
import client.view.gui.util.Util;
import common.response.Response;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        rootLayout.setCenter(null);
    }

    public void loadWindowFrameChoice(String[] reps, int[] tokens){
        rootLayout.setCenter(windowFrameChoicesPane);
        windowFrameChoiceController.setChoice(reps, tokens);
    }

    public void startGame(){
        rootLayout.setCenter(gamePane);
    }

    public void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens, int id) {
        this.id=id;
        for(int i=0; i<names.length; i++){
            GridPane frame = Util.getWindowFrame(windowFrameReps[i]);
            gameController.loadPlayer(names[i], ids[i], windowFrameReps[i], windowFrameFavorTokens[i]);
            if(ids[i]==id)
                gameController.setActiveFrame(frame, id);
        }
    }

    public void notifyChoice(int index) {
        try {
            remoteController.command(Response.CHOICE, index);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
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
        else return;
        gameController.log(message);
    }


    public void toolCardUsed(int player, int index, int tokens) {
        String message;
        if(player == id)
            message = "You ";
        else
            message = gameController.getPlayerName(player) + " ";
        message = message + "used "+gameController.getToolCardName(index) +  "\n";
        gameController.log(message);
        gameController.decreaseFavorTokens(player, tokens);
    }

    public void notifyDiceDraw(int player, String color) {
        String message;
        if(player==id)
            message="You drawed a " + color + " dice\n";
        else
            message = gameController.getPlayerName(player) + " drawed a " + color + " dice\n";
        gameController.log(message);
    }

    public void updateRoundTrack(int round, int[] values, char[] colors) {
        gameController.addRoundTrackBox(round, values, colors);
    }

    public void startTurn(int id) {
        if(id==this.id) {
            gameController.log("Is your turn!!\n");
            GamePhase.diceMoved=false;
            GamePhase.toolCardUsed=false;
            this.currentPhase=new MainPhase(remoteController, gameController);
        }
        else
            gameController.log("Is " + gameController.getPlayerName(id) + " turn!!\n");
    }

    public void handleResponse(Response response) {
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
            case WINDOW_FRAME_CELL:
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
            case SUCCESS:
                currentPhase=new MainPhase(remoteController,gameController);
                break;
            case ERROR:
                gameController.log("Something went wrong...\n");
                break;
            default:
                currentPhase=new MainPhase(remoteController, gameController);
                break;
        }
    }

    public void roundTrackClick(int round, int index) {
        currentPhase=currentPhase.handleRoundTrack(round, index);
    }

    public void toolCardClick(int index) {
        currentPhase=currentPhase.handleToolCard(index);
    }

    public void draftPoolClick(int index){
        currentPhase=currentPhase.handleDraftPool(index);
    }

    public void windowFrameClick(int row, int col){
        currentPhase=currentPhase.handleWindowFrame(row,col);
    }


    public void endTurn() {
        try {
            remoteController.command(Response.END_TURN);
            gameController.unableAll();
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentPhase=new MainPhase(remoteController, gameController);
    }
}
