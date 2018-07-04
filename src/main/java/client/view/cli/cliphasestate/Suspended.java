package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliLaunchClient;
import common.command.GameCommand;
import common.response.Response;
import server.LaunchServer;

import java.util.Scanner;

/**
 * The <tt>Suspended</tt> class implements the method which handles the input of the client while it is suspended.
 */
public class Suspended implements CliPhaseState {
    private final Scanner sc;
    private final String name;

    /**
     * @param sc
     * @param name
     */
    public Suspended(Scanner sc, String name){
        this.sc = sc;
        this.name = name;
        CliDisplayer.getDisplayer().displayText("You are suspended, press anything:\n>>>");
    }

    /**
     * Handles the input of the client while it is suspended. If the client press anything calls the method which
     * reconnect the client into the game.
     * @param input the input of the client.
     * @throws InvalidInput
     */
    @Override
    public void handle(String input) throws InvalidInput {
        CliLaunchClient.reconnect(sc, name);
        CliDisplayer.getDisplayer().displayText("Please wait your next turn!");
        CliApp.getCliApp().setWaitingPhase(true);
    }

    /**
     * Reset the Suspended's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return Suspended.
     */
    @Override
    public CliPhaseState reset() {
        return new Suspended(sc, name);
    }
}
