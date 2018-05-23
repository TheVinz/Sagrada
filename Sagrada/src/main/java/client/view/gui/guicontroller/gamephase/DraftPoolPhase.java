package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.rmi.RemoteException;

public class DraftPoolPhase extends GamePhase{


    public DraftPoolPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.draftPoolPhase();
    }

    @Override
    public GamePhase handleDraftPool(int index){
        try {
            controller.command(Response.DRAFT_POOL_CELL, index);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new GamePhase(controller, gameController);
    }
}
