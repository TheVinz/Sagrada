package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>DiluentePerPastaSaldaChoiche</tt> class implements the method which handles the {@link server.model.state.toolcards.TaglierinaManuale}'s choice.
 */
public class TaglierinaManualeChoice implements CliPhaseState {
    /**
     * Asks the client if he wants to move one {@link server.model.state.dice.Dice} of two Dice.
     */
    public TaglierinaManualeChoice(){
        CliDisplayer.getDisplayer().displayText("Insert 3 to move another dice, or 2 to stop:\n>>>");
    }

    /**
     * Handles the input of the client. If it is ok calls the method which allows the ToolCard to continue.
     * @param input the input of the client.
     * @throws InvalidInput if the input isn't two or three.
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
            if(nextInt !=2 && nextInt != 3){
                throw new InvalidInput("Wrong Input\n");
            }

            CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.CHOICE, nextInt));
            CliApp.getCliApp().sendCommand();
            CliApp.getCliApp().setWaitingPhase(true);
        }
    }

    /**
     * Reset the TaglierinaManualeChoice's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new TaglierinaManualeChoice.
     */
    @Override
    public CliPhaseState reset() {
        return new TaglierinaManualeChoice();
    }
}
