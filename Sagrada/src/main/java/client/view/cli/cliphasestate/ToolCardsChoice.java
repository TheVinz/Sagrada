package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ToolCardsChoice implements CliPhaseState {


    public ToolCardsChoice() {
        CliDisplayer.getDisplayer().displayText("Select the number of toolcards you want to play with:\n>>>");

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
            if(nextInt < 1 || nextInt > 5){
                throw new InvalidInput("Wrong Input\n");
            }

            CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.CHOICE, nextInt));
            CliApp.getCliApp().sendCommand();
            CliApp.getCliApp().setWaitingPhase(true);
        }
    }

    @Override
    public CliPhaseState reset() {
        return new ToolCardsChoice();
    }
}
