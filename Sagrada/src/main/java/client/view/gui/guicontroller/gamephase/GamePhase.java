package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;


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

    public GamePhase handleToolCard(int index){
        return this;
    }
    public GamePhase handleDraftPool(int index){
        return this;
    }
    public GamePhase handleWindowFrame(int row, int col){
        return this;
    }
    public GamePhase handleChoice(){
        return this;
    }
    public GamePhase handleRoundTrack(int round, int index){
        return this;
    }
}
