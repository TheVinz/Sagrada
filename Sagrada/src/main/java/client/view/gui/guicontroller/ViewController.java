package client.view.gui.guicontroller;

import client.view.gui.MainApp;
import client.view.gui.guimodel.GuiModel;
import client.view.gui.util.Util;
import common.response.Response;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.rmi.RemoteException;


public class ViewController {

    private RemoteController remoteController;
    private GameController gameController;
    private AnchorPane gamePane;
    private AnchorPane windowFrameChoicesPane;
    private WindowFrameChoiceController windowFrameChoiceController;
    private int id;

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

    public void notifyToolCardClicked(int index) {
        try {
            remoteController.command(Response.TOOL_CARD, index);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
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

    public void draftPoolClick(int index){
        try {
            remoteController.command(Response.DRAFT_POOL_CELL, index);
        } catch (InvalidMoveException e) {
            gameController.log(e.getMessage()+"\n");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void windowFrameClick(int row, int col){
        try {
            remoteController.command(Response.WINDOW_FRAME_CELL, row, col);
        } catch (InvalidMoveException e) {
            gameController.log(e.getMessage());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    public void endTurn() {
        try {
            remoteController.command(Response.END_TURN);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void startTurn(int id) {
        if(id==this.id)
            gameController.log("Is your turn!!\n");
        else
            gameController.log("Is " + gameController.getPlayerName(id) + " turn!!\n");
    }

    public void updateRoundTrack(int round, int[] values, char[] colors) {
        gameController.addRoundTrackBox(round, values, colors);
    }
}
