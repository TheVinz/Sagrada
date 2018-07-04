package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>ToolCardsChoice</tt> class implements the method which handles the choice of the number
 * of {@link server.model.state.toolcards.ToolCard}s in a SinglePlayerGame.
 */
public class ToolCardsChoice implements CliPhaseState {


    /**
     * Asks the client which is the number of ToolCards he wants to play.
     */
    public ToolCardsChoice() {
        CliDisplayer.getDisplayer().displayText("Select the number of toolcards you want to play with:\n>>>");

    }

    /**
     * Handles the input of the client. If is it ok calls the method which chose the ToolCard.
     * @param input the input of the client.
     * @throws InvalidInput if the input isn't between one and five.
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
            if(nextInt < 1 || nextInt > 5){
                throw new InvalidInput("Wrong Input\n");
            }

            CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.CHOICE, nextInt));
            CliApp.getCliApp().sendCommand();
            CliApp.getCliApp().setWaitingPhase(true);
        }
    }

    /**
     * Reset the ToolCardsChoice's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new ToolCardsChoice.
     */
    @Override
    public CliPhaseState reset() {
        return new ToolCardsChoice();
    }
}
