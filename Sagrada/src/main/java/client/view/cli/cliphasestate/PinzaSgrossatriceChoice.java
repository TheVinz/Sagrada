package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.command.GameCommand;
import common.response.Response;
import server.model.state.toolcards.PinzaSgrossatrice;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PinzaSgrossatriceChoice implements CliPhaseState {

    public PinzaSgrossatriceChoice(){
        CliDisplayer.getDisplayer().displayText("Insert 0 to increase or 1 to decrease:\n>>>");
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
        return new PinzaSgrossatriceChoice();
    }
}
