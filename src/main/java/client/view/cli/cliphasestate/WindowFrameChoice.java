package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>WindowFrameChoice</tt> class implements the method which handles the choice  of the
 * {@link server.model.state.boards.windowframe.WindowFrame} at the beginning of the game.
 */
public class WindowFrameChoice implements CliPhaseState {

    /**
     * Asks the client which WindowFrame he wants to play with.
     */
    public WindowFrameChoice() {
        System.out.print("\nSelect a ");
        CliDisplayer.getDisplayer().printBold("Window Frame\n>>>");
    }

    /**
     * Handles the input of the client. If it is ok call the method which assigned a WindowFrame to the client.
     * @param input the input of the client.
     * @throws InvalidInput if the input isn't between zero and three.
     */
    @Override
    public void handle(String input) throws InvalidInput {
        try (Scanner sc = new Scanner(input)) {
            int nextInt = -1;
            try {
                nextInt = sc.nextInt();
            } catch (InputMismatchException e) {
                throw new InvalidInput("Wrong Input\n");
            }
            if(nextInt < 0 || nextInt > 3){
                throw new InvalidInput("Wrong Input\n");
            }

            CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.CHOICE, nextInt));
            CliApp.getCliApp().sendCommand();
            CliApp.getCliApp().setWaitingPhase(true);
        }
    }

    /**
     * Reset the WindowFrameChoice's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new WindowFrameChoice.
     */
    @Override
    public CliPhaseState reset() {
        return new WindowFrameChoice();
    }
}
