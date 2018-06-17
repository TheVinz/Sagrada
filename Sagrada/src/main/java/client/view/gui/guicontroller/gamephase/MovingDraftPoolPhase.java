package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.io.IOException;
import java.rmi.RemoteException;

public class MovingDraftPoolPhase extends GamePhase{

    private int sourceIndex=-1;
    private int destRow;
    private int destCol;

    public MovingDraftPoolPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.movingDraftPoolPhase();
    }

    @Override
    public GamePhase handleDraftPool(int index){
        sourceIndex=index;
        return this;
    }
    @Override
    public GamePhase handleWindowFrame(int row, int col) throws IOException{
        destRow=row;
        destCol=col;
        new Thread(() -> {
            try {
                controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, destRow, destCol));
                controller.command(new GameCommand(Response.DRAFT_POOL_CELL, sourceIndex));
            } catch (IOException e) {
                gameController.suspend();
            }
        }).start();
        gameController.unableAll();
        return new GamePhase(controller, gameController);
    }
}
