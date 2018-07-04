package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliState;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>SelectingSendingToolCard</tt> class implements the method which handles the choice of a {@link server.model.state.toolcards.ToolCard}.
 */
public class SelectingSendingToolCard implements CliPhaseState{

    /**
     * Asks the client to choose the ToolCard he want to use.
     */
    public SelectingSendingToolCard(){
        CliDisplayer.getDisplayer().displayText("Choose a toolcard:\n>>> ");
    }

    /**
     * Handles the input of the client. If the input is ok calls the method which calls the selected ToolCard.
     * @param input the input of the client.
     * @throws InvalidInput if the input isn't a number between zero and the number of ToolCards in the game.
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
            if(nextInt < 0 || nextInt >= CliState.getCliState().getToolCardIds().length){
                throw new InvalidInput("Wrong Input\n");
            }
            CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.TOOL_CARD, nextInt));
            CliApp.getCliApp().sendCommand();
            CliApp.getCliApp().setWaitingPhase(true);
        }
    }

    /**
     * Reset the SelectingSendingToolCard's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new SelectingSendingToolCard.
     */
    @Override
    public CliPhaseState reset() {
        return new SelectingSendingToolCard();
    }
}
