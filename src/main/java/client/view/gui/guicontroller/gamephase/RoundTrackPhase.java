package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;

import java.io.IOException;

/**
 * The <tt>RoundTrackPhase</tt> class handles round track dice input requests by server. So it overrides
 * the only handleRoundTrack method by the {@link GamePhase} superclass.
 */
public class RoundTrackPhase extends GamePhase {

    /**
     * Initializes the <tt>RoundTrackPhase</tt> by setting the {@link RemoteController}
     * it will send the {@link GameCommand}s to, and the {@link GameController} of the current graphical
     * interface, and set the {@link GameController} graphical effects related to
     * this phase.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public RoundTrackPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.roundTrackPhase();
    }

    /**
     * Handles user's round track dice input, sending it as {@link GameCommand} to the server.
     * @param round the dice's round position.
     * @param index the dice's index in the round dice list.
     * @return new {@link GamePhase} to wait server response.
     * @throws IOException in case of connection drops.
     */
    @Override
    public GamePhase handleRoundTrack(int round, int index) throws IOException {
        controller.command(new GameCommand(Response.ROUND_TRACK_CELL, round, index));
        return new GamePhase(controller, gameController);
    }

}
