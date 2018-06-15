package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;

import java.rmi.Remote;
import java.rmi.RemoteException;


public class GamePhase {

    public static boolean toolCardUsed=false;
    public static boolean diceMoved=false;

    RemoteController controller;
    GameController gameController;

    public GamePhase(RemoteController controller, GameController gameController){
        this.controller=controller;
        this.gameController=gameController;
        gameController.unableAll();
    }

    public GamePhase handleToolCard(int index) throws RemoteException {
        return this;
    }
    public GamePhase handleDraftPool(int index)throws RemoteException {
        return this;
    }
    public GamePhase handleWindowFrame(int row, int col) throws RemoteException {
        return this;
    }
    public GamePhase handleChoice() throws RemoteException {
        return this;
    }
    public GamePhase handleRoundTrack(int round, int index) throws RemoteException {
        return this;
    }
}
