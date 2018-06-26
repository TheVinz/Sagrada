package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;
import javafx.application.Platform;

import java.io.IOException;

/**
 * The <tt>MainPhase</tt> is the first {@link GamePhase} set when a new user's turn begins,
 * in this phase he can:
 * <ul>
 *     <li>move a dice from the draft pool to his window frame, if he has not successfully moved one yet;</li>
 *     <li>start using a tool card, if he has not successfully used one yet;</li>
 * </ul>
 * so this class overrides handleDraftPool and handle WindowFrame methods, for moving dice,
 * and handleToolCard method, for using a tool card.
 * This phase is also set as {@link client.view.gui.guicontroller.ViewController} current
 * phase when:
 * <ul>
 *     <li>the input selected by the user represents an invalid move;</li>
 *     <li>the user successfully uses a tool card;</li>
 *     <li>the user successfully move a dice.</li>
 * </ul>
 */
public class MainPhase extends GamePhase {

    private boolean send = false;
    private int sourceIndex;

    /**
     * Initializes the <tt>mainPhase</tt> by setting the {@link RemoteController}
     * it will send the {@link GameCommand}s to, and the {@link GameController} of the current game graphical
     * interface, and set the {@link GameController} graphical effects related to
     * this phase.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public MainPhase(RemoteController controller, GameController gameController) {
        super(controller, gameController);
        gameController.mainPhase();
    }

    /**
     * Handle method for draft pool dice input by the user. Set the chosen dice as
     * source of a possible draft pool to window frame move. The return value
     * of this method is ignored.
     * @param index the dice's index.
     * @return this <tt>MainPhase</tt>.
     */
    @Override
    public GamePhase handleDraftPool(int index){
        sourceIndex=index;
        send = true;
        return this;
    }

    /**
     * Handle method for window frame cell input by the user. If a source dice from
     * the draft pool has already been set source and destination {@link GameCommand}s
     * are sent to the server.
     * @param row the cell's row.
     * @param col the cell's column.
     * @return a new GamePhase for waiting server response.
     *
     * @see Response
     */
    @Override
    public GamePhase handleWindowFrame(int row, int col) {
        if(send){
            new Thread(() -> {
                try {
                    send(sourceIndex, row, col);
                }catch(IOException e){
                    Platform.runLater(() -> gameController.suspend());
                }
            }).start();
        }
        return new GamePhase(controller, gameController);
    }

    private void send(int sourceIndex, int destRow, int destCol) throws IOException {
        controller.command(new GameCommand(Response.DRAFT_POOL_CELL, sourceIndex));
        controller.command(new GameCommand(Response.WINDOW_FRAME_CELL, destRow, destCol));
    }

    /**
     * Handle method for a tool card input by the user, sending tool card's
     * coordinates to the server.
     * @param index the tool card index.
     * @return a new GamePhase for waiting server response.
     * @throws IOException in case of connection drops.
     *
     * @see Response
     */
    @Override
    public GamePhase handleToolCard(int index) throws IOException {
        controller.command(new GameCommand(Response.TOOL_CARD, index));
        return new GamePhase(controller, gameController);
    }

}
