package client.view.cli.cliphasestate;


/**
 * The <tt>CliPhaseState</tt> is an interface class that represent the possible state where a {@link server.model.state.player.Player}
 * could find himself during the phase of the game.
 */
public interface CliPhaseState {
    /**
     * Handles the input of the Player.
     * @param input the input of the Player.
     * @throws InvalidInput exception called if the input is an InvalidInput.
     */
    void handle(String input) throws InvalidInput;

    CliPhaseState reset();
}
