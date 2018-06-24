package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;

import java.io.Closeable;
import java.rmi.Remote;
import java.io.IOException;


public class GamePhase implements Closeable {

    public static boolean toolCardUsed=false;
    public static boolean diceMoved=false;

    RemoteController controller;
    GameController gameController;

    public GamePhase(RemoteController controller, GameController gameController){
        this.controller=controller;
        this.gameController=gameController;
        gameController.unableAll();
    }

    public GamePhase handleToolCard(int index) throws IOException {
        return this;
    }
    public GamePhase handleDraftPool(int index)throws IOException {
        return this;
    }
    public GamePhase handleWindowFrame(int row, int col) throws IOException {
        return this;
    }
    public GamePhase handleChoice() throws IOException {
        return this;
    }
    public GamePhase handleRoundTrack(int round, int index) throws IOException {
        return this;
    }

    public void close(){}
}
