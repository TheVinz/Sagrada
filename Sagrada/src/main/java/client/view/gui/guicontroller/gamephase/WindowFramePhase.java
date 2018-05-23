package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.rmi.RemoteException;

public class WindowFramePhase extends GamePhase {
    public WindowFramePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.windowFramePhase();
    }

    @Override
    public GamePhase handleWindowFrame(int row, int index){
        try {
            controller.command(Response.WINDOW_FRAME_CELL, row, index);
        } catch (InvalidMoveException e) {
            gameController.log(e.getMessage()+"\n");
            return new GamePhase(controller, gameController);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new MainPhase(controller, gameController);
    }
}
