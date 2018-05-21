package client.view.gui.guimodel;

import client.view.gui.guicontroller.ViewController;
import common.RemoteMVC.RemoteView;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GuiModel extends UnicastRemoteObject implements RemoteView {

    private ViewController view;

    public GuiModel(ViewController view) throws RemoteException {
        super();
        this.view=view;
    }

    @Override
    public void move(int player, int sourceType, int destType, int param1, int param2, int param3) throws RemoteException {

    }

    @Override
    public void move(int player, int sourceType, int destType, int param1, int param2, int param3, int param4) throws RemoteException {

    }

    @Override
    public void updateCell(int player, int type, int index, int value, char color) throws RemoteException {

    }

    @Override
    public void updateCell(int player, int type, int param1, int param2, int value, char color) throws RemoteException {

    }

    @Override
    public void loadToolCards(int[] toolCards) throws RemoteException {

    }

    @Override
    public void refilledDraftPool(int[] values, char[] colors) throws RemoteException {

    }

    @Override
    public void loadPublicObjectiveCards(int[] cards) throws RemoteException {

    }

    @Override
    public void loadWindowFrameChoice(String[] reps, int[] favorTokens) throws RemoteException {
        //Multi-threading e javafx danno problemi, risolti con Platform.runLater()
        Platform.runLater(() -> view.loadWindowFrameChoice(reps, favorTokens));
    }

    @Override
    public void loadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens) throws RemoteException {

    }

    @Override
    public void toolCardUsed(int player, int index, int tokens) throws RemoteException {

    }

    @Override
    public void loadPrivateObjectiveCard(char color) throws RemoteException {

    }

    @Override
    public void newTurn(int player) throws RemoteException {

    }

    @Override
    public void notifyDiceDraw(int player, char color) throws RemoteException {

    }

    @Override
    public void updateRoundTrack(int round, int[] values, char[] colors) throws RemoteException {

    }
}
