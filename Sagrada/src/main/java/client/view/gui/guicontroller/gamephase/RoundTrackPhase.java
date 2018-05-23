package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.rmi.RemoteException;

public class RoundTrackPhase extends GamePhase {

    public RoundTrackPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.roundTrackPhase();
    }

    @Override
    public GamePhase handleRoundTrack(int round, int index){
        try {
            controller.command(Response.ROUND_TRACK_CELL, round, index);
        } catch (InvalidMoveException e) {
            gameController.log(e.getMessage()+"\n");
            return new MainPhase(controller,gameController);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new GamePhase(controller, gameController);
    }

}
