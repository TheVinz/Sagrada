package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;

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
    public GamePhase handleWindowFrame(int row, int col) throws RemoteException {
        destRow=row;
        destCol=col;
        controller.command(new GameCommand(Response.DRAFT_POOL_CELL, sourceIndex));
        controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, destRow, destCol));
        gameController.unableAll();
        return new GamePhase(controller, gameController);
    }
}
