package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>DiluentePerPastaSaldaChoiche</tt> class implements the method which handles the {@link server.model.state.toolcards.DiluentePerPastaSalda}'s choice.
 */
public class DiluentePerPastaSaldaChoice implements CliPhaseState {

    /**
     * Asks to the client to insert the new value of the drafted {@link server.model.state.dice.Dice}.
     */
    public DiluentePerPastaSaldaChoice(){
        CliDisplayer.getDisplayer().displayText("Insert the new value:\n>>>");
    }

    /**
     * Handles the input of the client. If the input is ok calls the method which allows the ToolCard to continue.
     * @param input the input of the client in the terminal.
     * @throws InvalidInput if the input is an int lower of one or bigger of six.
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
            if(nextInt < 1 || nextInt > 6){
                throw new InvalidInput("Wrong Input\n");
            }

            CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.CHOICE, nextInt));
            CliApp.getCliApp().sendCommand();
            CliApp.getCliApp().setWaitingPhase(true);
        }
    }

    /**
     * Reset the DiluentePerPastaSaldaChoice's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new DiluentePerPastaSaldaChoice.
     */
    @Override
    public CliPhaseState reset() {
        return new DiluentePerPastaSaldaChoice();
    }
}
