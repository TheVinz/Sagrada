package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliState;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingSendingPlayerWindowFrame implements CliPhaseState {

    public SelectingSendingPlayerWindowFrame(){
        CliDisplayer.getDisplayer().displayText("Put the name of the player:\n");
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
            if(nextInt < 0 || nextInt > 4){
                throw new InvalidInput("Wrong Input\n");
            }
            CliDisplayer.getDisplayer().printWindowFrame(CliState.getCliState().getCliPlayerState(nextInt));
            CliApp.getCliApp().sendCommand();
        }
    }
    @Override
    public CliPhaseState reset() {
        return new SelectingSendingPlayerWindowFrame();
    }
}
