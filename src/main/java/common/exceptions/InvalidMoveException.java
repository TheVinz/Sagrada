package common.exceptions;

/**
 * The <tt>InvalidMoveException</tt> describes the rejection of an user's input by the server, aborting the current move the {@link server.model.state.player.Player}
 * was performing and resetting his state to a clean state, such as the {@link server.controller.WaitingState} were the player can
 * either use a {@link server.model.state.toolcards.ToolCard}'s effect or perform a {@link server.model.state.boards.draftpool.DraftPool} to
 * {@link server.model.state.boards.windowframe.WindowFrame} dice move.
 */
public class InvalidMoveException extends Exception{

    /**
     * Initializes a new <tt>InvalidMoveException</tt> with the given message.
     * @param message the exception's message.
     */
    public InvalidMoveException(String message){
        super(message);
    }
}
