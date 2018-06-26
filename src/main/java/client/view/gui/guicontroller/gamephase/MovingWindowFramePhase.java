package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;

import java.io.IOException;

/**
 * The <tt>MovingWindowFramePhase</tt> class is used to handle user's inputs in case
 * of window frame to window frame dice move by the server. This class overrides only
 * the handleWindowFrame method from the {@link GamePhase} superclass.
 */
public class MovingWindowFramePhase extends GamePhase {

    private boolean first = true;
    private int sourceRow;
    private int sourceCol;

    /**
     * Initializes the <tt>MovingWindowFramePhase</tt> by setting the {@link RemoteController}
     * it will send the {@link GameCommand}s to, and the {@link GameController} of the current graphical
     * interface, and set the {@link GameController} graphical effects related to
     * this phase.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public MovingWindowFramePhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.movingWindowFrame();
    }

    /**
     * Handle a user's window frame cell input, selecting this cell as source for
     * a possible move, if source has not been set yet, or as target, sending
     * source and destination to the server as {@link GameCommand}s. If source and
     * target are the same cell, the move is not performed.
     * @param row the cell's row.
     * @param col the cell's column.
     * @return a new {@link GamePhase} for waiting server response if the move is performed,
     * a new <tt>MovingWindowFramePhase</tt> otherwise.
     *
     * @see Response
     */
    @Override
    public GamePhase handleWindowFrame(int row, int col) {
        if(first){
            sourceRow=row;
            sourceCol=col;
            first=false;
            return this;
        }
        else {
            if (sourceRow == row && sourceCol == col)
                return new MovingWindowFramePhase(controller, gameController);
            else {
                new Thread(() -> {
                    try {
                        controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, sourceRow, sourceCol));
                        controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, row, col));
                    } catch (IOException e) {
                        gameController.suspend();
                    }
                }).start();
                return new GamePhase(controller, gameController);
            }
        }
    }
}
