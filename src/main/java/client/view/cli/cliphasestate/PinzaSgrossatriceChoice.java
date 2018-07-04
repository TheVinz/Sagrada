package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;
import server.model.state.toolcards.PinzaSgrossatrice;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>PinzaSgrossatriceChoice</tt> class implements the method which handles the {@link server.model.state.toolcards.PinzaSgrossatrice}'s choice.
 */
public class PinzaSgrossatriceChoice implements CliPhaseState {

    /**
     * Asks the client to insert 0 to increase the value of the {@link server.model.state.dice.Dice} or to decrease it.
     */
    public PinzaSgrossatriceChoice(){
        CliDisplayer.getDisplayer().displayText("Insert 0 to increase or 1 to decrease:\n>>>");
    }

    /**
     * Handles the input of the client. If the input is ok calls the method which allows the ToolCard to continue.
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
     * Reset the PinzaSgrossatriceChoice's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new PinzaSgrossatriceChoice.
     */
    @Override
    public CliPhaseState reset() {
        return new PinzaSgrossatriceChoice();
    }
}
