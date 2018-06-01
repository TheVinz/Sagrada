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
    public synchronized void move(int player, Response sourceType, Response destType, int param1, int param2, int param3) {
        Platform.runLater(() -> view.move(player, sourceType, destType, param1, param2, param3));
    }

    @Override
    public synchronized void move(int player, Response sourceType, Response destType, int param1, int param2, int param3, int param4) {
        Platform.runLater(() -> view.move(player, sourceType, destType, param1, param2, param3, param4));
    }

    @Override
    public synchronized void updateCell(int player, Response type, int index, int value, char color) {
        Platform.runLater(() -> view.updateCell(player, type, index, value, color));
    }

    @Override
    public synchronized void updateCell(int player, Response type, int param1, int param2, int value, char color) {
        Platform.runLater(() -> view.updateCell(player, type, param1, param2, value, color));
    }

    @Override
    public synchronized void loadToolCards(int[] toolCards) {
        Platform.runLater(() -> view.loadToolCard(toolCards));
    }

    @Override
    public synchronized void refilledDraftPool(int[] values, char[] colors) {
        Platform.runLater(() -> view.setDraftPool(values, colors));
    }

    @Override
    public synchronized void loadPublicObjectiveCards(int[] cards) {
        Platform.runLater(() -> view.setPublicObjectiveCards(cards));
    }

    @Override
    public synchronized void loadWindowFrameChoice(String[] reps, int[] favorTokens) {
        //Multi-threading e javafx danno problemi, risolti con Platform.runLater()
        Platform.runLater(() -> view.loadWindowFrameChoice(reps, favorTokens));
    }

    @Override
    public synchronized void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens) {
        Platform.runLater(() -> view.loadPlayers(names, ids, windowFrameReps, windowFrameFavorTokens, id));
    }

    @Override
    public synchronized void toolCardUsed(int player, int index, int tokens) {
        Platform.runLater(() -> view.toolCardUsed(player, index, tokens));

    }

    @Override
    public synchronized void loadPrivateObjectiveCard(char color) {
        Platform.runLater(() -> view.loadPrivateObjectiveCard(color));
    }

    @Override
    public synchronized void newTurn(int player) {
        Platform.runLater(() -> view.startTurn(player));
    }

    @Override
    public synchronized void notifyDiceDraw(int player, char color) {
        Platform.runLater(() -> view.notifyDiceDraw(player, color));
    }

    @Override
    public synchronized void updateRoundTrack(int round, int[] values, char[] colors) {
        Platform.runLater(() -> view.updateRoundTrack(round, values, colors));
    }

    @Override
    public synchronized void setId(int id){
        this.id=id;
    }

    @Override
    public synchronized void nextParameter(Response response) {
        Platform.runLater(() -> view.handleResponse(response));
    }

    @Override
    public synchronized void error(String message) {
        Platform.runLater(() -> view.error(message));
    }

    @Override
    public synchronized void wrongParameter(String message) {
        Platform.runLater(() -> view.debug(message));
    }

    @Override
    public synchronized void endGame(char[] cards, int[] scoreboardIds,int[][] matrixPoints) {
        Platform.runLater(() -> view.endGame(cards, scoreboardIds, matrixPoints));
    }

    @Override
    public synchronized void mutableData(int[] draftPoolValues, char[] draftPoolColors, int[][] roundTrackValues, char[][] roundTrackColors, String[] names, int[] ids, int[] favorTokens, String[] windowFrameReps, int[][][] windowFrameValues, char[][][] windowFrameColors) {
        Platform.runLater(() -> {
            view.startGame();
            view.setDraftPool(draftPoolValues, draftPoolColors);
            for(int i=1; i<roundTrackValues.length; i++) {
                view.updateRoundTrack(i, roundTrackValues[i], roundTrackColors[i]);
            }
            view.loadPlayers(names, ids, windowFrameReps, favorTokens, this.id);
            for(int i=0; i<ids.length; i++) {
                view.setWindowFrameDices(ids[i], windowFrameValues[i], windowFrameColors[i]);
            }
            view.startGame();
        });
    }

    @Override
    public synchronized void reinsertPlayer(int id) throws RemoteException {
        Platform.runLater(() -> view.notifyPlayerReconnected(id));
    }

    @Override
    public synchronized void suspendPlayer(int id) throws RemoteException {
        Platform.runLater(() -> view.notifyPlayerDisconnected(id));
    }

    @Override
    public synchronized void toolCardsChoice() throws RemoteException {
        Platform.runLater(() -> view.choseDifficulty());
    }

    @Override
    public synchronized void removeDice(int id, Response draftPoolCell, int index) throws RemoteException {
        Platform.runLater(() -> view.removeDraftPoolDice(index));
    }

}
