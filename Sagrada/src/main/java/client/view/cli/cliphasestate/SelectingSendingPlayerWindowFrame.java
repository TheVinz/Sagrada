package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliState;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingSendingPlayerWindowFrame implements CliPhaseState {

    public SelectingSendingPlayerWindowFrame(){
        CliDisplayer.getDisplayer().displayText("Other Players are:");
        for(int i=0;i<4;i++){
            if(CliState.getCliState().getCliPlayerState(i)!=null)
                CliDisplayer.getDisplayer().displayText(CliState.getCliState().getCliPlayerState(i).getId()+"_"+CliState.getCliState().getCliPlayerState(i).getName()+" | ");
        }
        CliDisplayer.getDisplayer().displayText("\n Press e to exit or Put the id of the player:\n");
    }
    @Override
    public void handle(String input) throws InvalidInput {
        if (input == "e") {   //non me lo vede
            CliApp.getCliApp().setCurrentState(new MenuPhase());
        }
        else {
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
                //passo il nome per non cambiare il clidisplayer
                CliApp.getCliApp().sendCommand();    //si blocca qua secondo me
                CliApp.getCliApp().setCurrentState(new MenuPhase());   //aggiunto io
                CliApp.getCliApp().setWaitingPhase(true);    //per me non ci va
            }
        }
    }
    @Override
    public CliPhaseState reset() {
        return new SelectingSendingPlayerWindowFrame();
    }
}
