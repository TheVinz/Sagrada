package client.view.gui.guimodel;

import client.view.gui.guicontroller.ViewController;
import common.RemoteMVC.RemoteView;
import common.response.Response;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GuiModel extends UnicastRemoteObject implements RemoteView {

    private ViewController view;
    private int id;

    public GuiModel(ViewController view) throws RemoteException {
        super();
        this.view=view;
    }

    @Override
    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3) {
        Platform.runLater(() -> view.move(player, sourceType, destType, param1, param2, param3));
    }

    @Override
    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3, int param4) {
        Platform.runLater(() -> view.move(player, sourceType, destType, param1, param2, param3, param4));
    }

    @Override
    public void updateCell(int player, Response type, int index, int value, char color) {
        Platform.runLater(() -> view.updateCell(player, type, index, value, color));
    }

    @Override
    public void updateCell(int player, Response type, int param1, int param2, int value, char color) {
        Platform.runLater(() -> view.updateCell(player, type, param1, param2, value, color));
    }

    @Override
    public void loadToolCards(int[] toolCards) {
        Platform.runLater(() -> view.loadToolCard(toolCards));
    }

    @Override
    public void refilledDraftPool(int[] values, char[] colors) {
        Platform.runLater(() -> view.setDraftPool(values, colors));
    }

    @Override
    public void loadPublicObjectiveCards(int[] cards) {
        Platform.runLater(() -> view.setPublicObjectiveCards(cards));
    }

    @Override
    public void loadWindowFrameChoice(String[] reps, int[] favorTokens) {
        //Multi-threading e javafx danno problemi, risolti con Platform.runLater()
        Platform.runLater(() -> view.loadWindowFrameChoice(reps, favorTokens));
    }

    @Override
    public void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens) {
        Platform.runLater(() -> view.loadPlayers(names, ids, windowFrameReps, windowFrameFavorTokens, id));
    }

    @Override
    public void toolCardUsed(int player, int index, int tokens) {
        Platform.runLater(() -> view.toolCardUsed(player, index, tokens));

    }

    @Override
    public void loadPrivateObjectiveCard(char color) {
        Platform.runLater(() -> view.loadPrivateObjectiveCard(color));
    }

    @Override
    public void newTurn(int player) {
        Platform.runLater(() -> view.startTurn(player));
    }

    @Override
    public void notifyDiceDraw(int player, char color) {
        Platform.runLater(() -> view.notifyDiceDraw(player, color));
    }

    @Override
    public void updateRoundTrack(int round, int[] values, char[] colors) {
        Platform.runLater(() -> view.updateRoundTrack(round, values, colors));
    }

    @Override
    public void setId(int id){
        this.id=id;
    }

    @Override
    public void nextParameter(Response response) {
        Platform.runLater(() -> view.handleResponse(response));
    }

    @Override
    public void error(String message) {
        Platform.runLater(() -> view.error(message));
    }

    @Override
    public void wrongParameter(String message) {
        Platform.runLater(() -> view.debug(message));
    }

    @Override
    public void endGame(char[] cards, int[] scoreboardIds,int[][] matrixPoints) {
        Platform.runLater(() -> view.endGame(cards, scoreboardIds, matrixPoints));
    }

}
