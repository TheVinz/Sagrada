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
    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3) throws RemoteException {
        Platform.runLater(() -> view.move(player, sourceType, destType, param1, param2, param3));
    }

    @Override
    public void move(int player, Response sourceType, Response destType, int param1, int param2, int param3, int param4) throws RemoteException {

    }

    @Override
    public void updateCell(int player, Response type, int index, int value, char color) throws RemoteException {

    }

    @Override
    public void updateCell(int player, Response type, int param1, int param2, int value, char color) throws RemoteException {

    }

    @Override
    public void loadToolCards(int[] toolCards) throws RemoteException {
        Platform.runLater(() -> view.loadToolCard(toolCards));
    }

    @Override
    public void refilledDraftPool(int[] values, char[] colors) throws RemoteException {
        Platform.runLater(() -> view.setDraftPool(values, colors));
    }

    @Override
    public void loadPublicObjectiveCards(int[] cards) throws RemoteException {
        Platform.runLater(() -> view.setPublicObjectiveCards(cards));
    }

    @Override
    public void loadWindowFrameChoice(String[] reps, int[] favorTokens) throws RemoteException {
        //Multi-threading e javafx danno problemi, risolti con Platform.runLater()
        Platform.runLater(() -> view.loadWindowFrameChoice(reps, favorTokens));
    }

    @Override
    public void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens) throws RemoteException {
        Platform.runLater(() -> view.loadPlayers(names, ids, windowFrameReps, windowFrameFavorTokens, id));
    }

    @Override
    public void toolCardUsed(int player, int index, int tokens) throws RemoteException {
        Platform.runLater(() -> view.toolCardUsed(player, index, tokens));

    }

    @Override
    public void loadPrivateObjectiveCard(char color) throws RemoteException {
        Platform.runLater(() -> view.loadPrivateObjectiveCard(color));
    }

    @Override
    public void newTurn(int player) throws RemoteException {
        Platform.runLater(() -> view.startTurn(player));
    }

    @Override
    public void notifyDiceDraw(int player, char color) throws RemoteException {
        String diceColor;
        switch(color){
            case 'b':
                diceColor="blue";
                break;
            case 'r':
                diceColor= "red";
                break;
            case 'y':
                diceColor="yellow";
                break;
            case 'p':
                diceColor="purple";
                break;
            case 'g':
                diceColor="green";
                break;
            default:
                diceColor=null;
                break;
        }
        Platform.runLater(() -> view.notifyDiceDraw(player, diceColor));
    }

    @Override
    public void updateRoundTrack(int round, int[] values, char[] colors) throws RemoteException {
        Platform.runLater(() -> view.updateRoundTrack(round, values, colors));
    }

    @Override
    public void setId(int id) throws RemoteException{
        this.id=id;
    }

    @Override
    public void nextParameter(Response response) {
        view.debug("Response received\n");
        Platform.runLater(() -> view.handleResponse(response));
    }
}
