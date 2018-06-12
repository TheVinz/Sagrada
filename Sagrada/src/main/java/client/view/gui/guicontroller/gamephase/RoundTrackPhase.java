package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.rmi.RemoteException;

public class RoundTrackPhase extends GamePhase {

    public RoundTrackPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.roundTrackPhase();
    }

    @Override
    public GamePhase handleRoundTrack(int round, int index) throws RemoteException {
        controller.command(new GameCommand(Response.ROUND_TRACK_CELL, round, index));
        return new GamePhase(controller, gameController);
    }

}
