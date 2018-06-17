package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.io.IOException;
import java.rmi.RemoteException;

public class DraftPoolPhase extends GamePhase{


    public DraftPoolPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.draftPoolPhase();
    }

    @Override
    public GamePhase handleDraftPool(int index) throws IOException {
        controller.command(new GameCommand(Response.DRAFT_POOL_CELL, index));
        return new GamePhase(controller, gameController);
    }
}
