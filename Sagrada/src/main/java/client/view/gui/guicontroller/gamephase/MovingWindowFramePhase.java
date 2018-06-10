package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.rmi.RemoteException;

public class MovingWindowFramePhase extends GamePhase {

    private boolean first = true;
    private int sourceRow;
    private int sourceCol;
    private int targetRow;
    private int targetCol;

    public MovingWindowFramePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.movingWindowFrame();
    }

    @Override
    public GamePhase handleWindowFrame(int row, int col){
        if(first){
            sourceRow=row;
            sourceCol=col;
            first=false;
            return this;
        }
        else {
            targetRow = row;
            targetCol = col;
            if (sourceRow == targetRow && sourceCol == targetCol)
                return new MovingWindowFramePhase(controller, gameController);
            else {
                try {
                    controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, sourceRow, sourceCol));
                    controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, targetRow, targetCol));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return new GamePhase(controller, gameController);
            }
        }
    }
}
