package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;

import java.io.IOException;

/**
 * The <tt>MovingDraftPool</tt> class is the {@link GamePhase} that handles the
 * draft pool to window frame moves by the server, so it just overrides the handleDraftPool
 * and handleWindowFrame methods.
 */
public class MovingDraftPoolPhase extends GamePhase{

    private boolean send = false;
    private int sourceIndex;

    /**
     * Initializes the <tt>MovingDraftPoolPhase</tt> by setting the {@link RemoteController}
     * it will send the {@link GameCommand}s to, and the {@link GameController} of the current graphical
     * interface, and set the {@link GameController} graphical effects related to
     * this phase.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public MovingDraftPoolPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.movingDraftPoolPhase();
    }

    /**
     * Handle method for user's draft pool dice input, setting this dice a source
     * for a possible draft pool to window frame move.
     * @param index the dice's index.
     * @return this <tt>MovingDraftPoolPhase</tt>.
     */
    @Override
    public GamePhase handleDraftPool(int index){
        sourceIndex=index;
        send = true;
        return this;
    }

    /**
     * Handle method for user's window frame cell input, sending move source
     * and destination as {@link GameCommand} to the server if a draft pool dice
     * has already been selected.
     * @param row the cell's row.
     * @param col the cell's column.
     * @return a new {@link GamePhase} if the move commands are sent to the server,
     * <code>this</code> otherwise.
     * @throws IOException in case of connection drops.
     */
    @Override
    public GamePhase handleWindowFrame(int row, int col) throws IOException{
        if(send) {
            new Thread(() -> {
                try {
                    controller.command(new GameCommand(Response.DRAFT_POOL_CELL, sourceIndex));
                    controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, row, col));
                } catch (IOException e) {
                    gameController.suspend();
                }
            }).start();
            return new GamePhase(controller, gameController);
        }
        else return this;
    }
}
