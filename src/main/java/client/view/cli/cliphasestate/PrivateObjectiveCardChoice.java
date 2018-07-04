package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>PrivateObjectiveCardChoice</tt> class implements the method which handles the choice of the client (SinglePlayer mode) regarding
 * on which {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard} use to calculates the final points.
 */
public class PrivateObjectiveCardChoice implements CliPhaseState{
    /**
     * Prints the {@link server.model.state.boards.windowframe.WindowFrame} of the client and the two available PrivateObjectiveCard.
     * Then asks the client to choose the PrivateObjectiveCard.
     */
    public PrivateObjectiveCardChoice() {
        CliDisplayer.getDisplayer().printWindowFrame();
        CliDisplayer.getDisplayer().printPrivateObjectiveCard();
        System.out.print("\nInsert 0 or 1:\n>>> ");
    }

    /**
     * Handles the input of the client. If the input is ok calls the method which allows to continue the final phase.
     * @param input the input of the client in the terminal.
     * @throws InvalidInput if the input isn't zero or one.
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
            if(nextInt != 0 && nextInt != 1){
                throw new InvalidInput("Wrong Input\n");
            }

            CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.CHOICE, nextInt));
            CliApp.getCliApp().sendCommand();
            CliApp.getCliApp().setWaitingPhase(true);
        }
    }

    /**
     * Reset the PrivateObjectiveCardChoice's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new PrivateObjectiveCardChoice.
     */
    @Override
    public CliPhaseState reset() {
        return new PrivateObjectiveCardChoice();
    }
}
