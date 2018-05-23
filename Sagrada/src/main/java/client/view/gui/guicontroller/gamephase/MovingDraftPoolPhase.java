package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
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
    public GamePhase handleWindowFrame(int row, int col){
        destRow=row;
        destCol=col;
        try {
            controller.command(Response.DRAFT_POOL_CELL, sourceIndex);
            controller.command(Response.WINDOW_FRAME_CELL, destRow, destCol);
        } catch (InvalidMoveException e) {
            gameController.log(e.getMessage() + "\n");
            return new MainPhase(controller,gameController);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        gameController.unableAll();
        return new GamePhase(controller, gameController);
    }
}
