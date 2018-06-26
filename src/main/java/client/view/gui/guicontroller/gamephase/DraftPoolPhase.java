package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;

import java.io.IOException;

/**
 * The <tt>DraftPoolPhase</tt> class handles users input in case of server draft pool
 * dice request, so it just overrides the handleDraftPool method from the {@link GamePhase}
 * class.
 */
public class DraftPoolPhase extends GamePhase{

    /**
     * Initializes the <tt>DraftPoolPhase</tt> by setting the {@link RemoteController}
     * it will send the {@link GameCommand}s to, and the {@link GameController} of the current graphical
     * interface, and set the {@link GameController} graphical effects related to
     * this phase.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public DraftPoolPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.draftPoolPhase();
    }

    /**
     * Sends the chosen draft pool dice coordinates to the server.
     * @param index the chosen draft pool dice index.
     * @return a new {@link GamePhase} for waiting server response.
     * @throws IOException in case of connection drops.
     */
    @Override
    public GamePhase handleDraftPool(int index) throws IOException {
        controller.command(new GameCommand(Response.DRAFT_POOL_CELL, index));
        return new GamePhase(controller, gameController);
    }
}
