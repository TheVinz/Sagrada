package common.exceptions;

/**
 * The <tt>WrongParameter</tt> exception is thrown when the input sent by the client represents a not valid move, so the
 * input is rejected but the move is not aborted and the server asks the client to retry with a different input.
 */
public class WrongParameter extends Exception {

    /**
     * Initializes a new <tt>WrongParameter</tt> with the given message.
     * @param message the exception's message.
     */
    public WrongParameter(String message){
        super(message);
    }
}
