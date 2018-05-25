package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliState;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingPlayerWindowFrame implements CliPhaseState {

    public SelectingPlayerWindowFrame(){
        CliDisplayer.getDisplayer().displayText("Other Players are: ");
        for(int i=0;i<4;i++){
            if(CliState.getCliState().getCliPlayerState(i)!=null)
                CliDisplayer.getDisplayer().displayText("Id: "+CliState.getCliState().getCliPlayerState(i).getId()+"_Name: "+CliState.getCliState().getCliPlayerState(i).getName()+" | ");
        }
        CliDisplayer.getDisplayer().displayText("\nPut the id of the player:\n>>>");
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
                if (nextInt < 0 || nextInt > 4) {
                    throw new InvalidInput("Wrong Input\n");
                }
                CliDisplayer.getDisplayer().printState(CliState.getCliState().getCliPlayerState(nextInt).getName());
                CliApp.getCliApp().setCurrentState(new MenuPhase());
            }
    }
    @Override
    public CliPhaseState reset() {
        return new SelectingPlayerWindowFrame();
    }
}
