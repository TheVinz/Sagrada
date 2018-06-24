package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PrivateObjectiveCardChoice implements CliPhaseState{
    public PrivateObjectiveCardChoice() {
        CliDisplayer.getDisplayer().printWindowFrame();
        CliDisplayer.getDisplayer().printPrivateObjectiveCard();
        System.out.print("\nInsert 0 or 1:\n>>> ");
    }

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

    @Override
    public CliPhaseState reset() {
        return new PrivateObjectiveCardChoice();
    }
}
