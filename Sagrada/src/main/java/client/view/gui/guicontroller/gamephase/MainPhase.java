package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.rmi.RemoteException;

public class MainPhase extends GamePhase {

    private int sourceIndex=-1;
    private int destRow;
    private int destCol;

    public MainPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.mainPhase();
    }

    @Override
    public GamePhase handleDraftPool(int index){
        try {
            controller.command(Response.DRAFT_POOL_CELL, index);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        sourceIndex=index;
        return this;
    }

    @Override
    public GamePhase handleWindowFrame(int row, int col){
        destRow=row;
        destCol=col;
        if(sourceIndex!=-1){
            try {
                controller.command(Response.DRAFT_POOL_CELL, sourceIndex);
                controller.command(Response.WINDOW_FRAME_CELL, destRow, destCol);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            diceMoved=true;
        }
        return new GamePhase(controller, gameController);
    }

    @Override
    public GamePhase handleToolCard(int index){
        try {
            controller.command(Response.TOOL_CARD, index);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        GamePhase phase = new GamePhase(controller, gameController);
        return phase;
    }

}
