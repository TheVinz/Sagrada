package client.view.cli.cliphasestate;

/**
 * The <tt>InvalidInput</tt> class implements an exception which will be called if the {@link server.model.state.player.Player} put a wrong input.
 */
public class InvalidInput extends Exception {
    public InvalidInput(String message){
        super(message);
    }
}
