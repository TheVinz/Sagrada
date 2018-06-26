package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;

import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;

import java.io.IOException;

/**
 * The <tt>WindowFramePhase</tt> handles user's input in case of window frame cell request
 * by the server, so this class only overrides handleWindowFrame method from {@link GamePhase}
 * superclass.
 */
public class WindowFramePhase extends GamePhase {

    /**
     * Initializes the <tt>WindowFramePhase</tt> by setting the {@link RemoteController}
     * it will send the {@link GameCommand}s to, and the {@link GameController} of the current graphical
     * interface, and set the {@link GameController} graphical effects related to
     * this phase.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public WindowFramePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.windowFramePhase();
    }

    /**
     * Handle user's window frame cell input, sending a new {@link GameCommand} to the server representing it.
     * @param row the cell's row.
     * @param col the cell's column.
     * @return a new {@link GamePhase} for waiting server response.
     * @throws IOException in case of connection drops.
     *
     * @see Response
     */
    @Override
    public GamePhase handleWindowFrame(int row, int col) throws IOException {
        controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, row, col));
        return new GamePhase(controller, gameController);
    }
}
