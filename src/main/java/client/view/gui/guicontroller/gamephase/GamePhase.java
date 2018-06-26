package client.view.gui.guicontroller.gamephase;

import client.view.gui.guicontroller.GameController;
import common.RemoteMVC.RemoteController;

import java.io.Closeable;
import java.io.IOException;

/**
 * The <tt>GamePhase</tt> class is the superclass of all the possible phases for the
 * {@link client.view.gui.guicontroller.ViewController}. Each phase is created for
 * handling all the different kind of inputs requested by the server. Particularly
 * the generic GamePhase is used as "waiting phase": if it's not the user's turn
 * or if this client is waiting for a server {@link common.response.Response}, a
 * GamePhase is set as ViewController's current phase, disabling every possible input
 * the user can submit. This class also offers static methods for set and get of boolean
 * values in order to keep track of user's possibility of moving dices or using tool
 * cards.
 */
public class GamePhase implements Closeable {

    private static boolean toolCardUsed=false;
    private static boolean diceMoved=false;

    RemoteController controller;
    GameController gameController;

    /**
     * Initializes the <tt>GamePhase</tt> by setting the {@link RemoteController}
     * it will send the {@link common.command.GameCommand}s to, and the {@link GameController} of the current graphical
     * interface, and set the {@link GameController} graphical effects related to
     * this phase.
     * @param controller the RemoteController for sending commands.
     * @param gameController the GameController of the GUI.
     */
    public GamePhase(RemoteController controller, GameController gameController){
        this.controller=controller;
        this.gameController=gameController;
        gameController.gamePhase();
    }

    /**
     * Set the toolCardUsed value to <code>true</code>.
     */
    public static void setToolCardUsed(){
        toolCardUsed=true;
    }

    /**
     * Set the diceMoved value to <code>true</code>
     */
    public static void setDiceMoved(){
        diceMoved=true;
    }
    /**
     * Set the diceMoved value to <code>false</code>
     */
    public static void unsetDiceMoved(){
        diceMoved=false;
    }
    /**
     * Set the toolCardUsed value to <code>false</code>
     */
    public static void unsetToolCardUsed(){
        toolCardUsed=false;
    }

    /**
     * @return <code>true</code> if the player has already moved a dice during this
     * turn, <code>false</code> otherwise.
     */
    public static boolean isDiceMoved(){
        return diceMoved;
    }

    /**
     * @return <code>true</code> if the player has already used a tool card
     * during this turn, <code>false</code> otherwise.
     */
    public static boolean isToolCardUsed(){
        return toolCardUsed;
    }

    /**
     * Handle method for user's tool card input, ignoring it.
     * @param index the tool card index.
     * @return this <tt>GamePhase</tt>
     * @throws IOException in case of connection drops.
     */
    public GamePhase handleToolCard(int index) throws IOException {
        return this;
    }

    /**
     * Handle method for user's draft pool dice input, ignoring it.
     * @param index the dice's index.
     * @return this <tt>GamePhase</tt>.
     * @throws IOException in case of connection drops.
     */
    public GamePhase handleDraftPool(int index)throws IOException {
        return this;
    }

    /**
     * Handle method for user'd window frame cell input, ignoring it.
     * @param row the cell's row.
     * @param col the cell's column.
     * @return this <tt>GamePhase</tt>.
     * @throws IOException in case of connection drops.
     */
    public GamePhase handleWindowFrame(int row, int col) throws IOException {
        return this;
    }

    /**
     * Method used in case of choice request by the server, only used by subclasses.
     * @return this <tt>GamePhase</tt>.
     * @throws IOException in case of connection drops.
     */
    public GamePhase handleChoice() throws IOException {
        return this;
    }

    /**
     * Handle method for user's round track dice input, ignoring it.
     * @param round the dice's round position.
     * @param index the dice's index in the round dice list.
     * @return this <tt>GamePhase</tt>.
     * @throws IOException in case of connection drops.
     */
    public GamePhase handleRoundTrack(int round, int index) throws IOException {
        return this;
    }

    /**
     * Method called in case of connection drops, used by subclasses.
     */
    public void close(){}
}
