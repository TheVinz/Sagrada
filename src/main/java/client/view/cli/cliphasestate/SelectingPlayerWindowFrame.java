package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliState;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>SelectingPlayerWindowFrame</tt> class implements the method which handles the prints of the {@link server.model.state.boards.windowframe.WindowFrame}
 * of a {@link server.model.state.player.Player}.
 */
public class SelectingPlayerWindowFrame implements CliPhaseState {

    /**
     * Prints the Players in the game with their id and name. Asks to the client to put the id of the Player whose
     * WindowFrame is gonna be printed.
     */
    public SelectingPlayerWindowFrame(){
        CliDisplayer.getDisplayer().displayText("Other LoadPlayers are: ");
        for(int i=0;i<4;i++){
            if(CliState.getCliState().getCliPlayerState(i)!=null)
                CliDisplayer.getDisplayer().displayText("Id: "+CliState.getCliState().getCliPlayerState(i).getId()+"_Name: "+CliState.getCliState().getCliPlayerState(i).getName()+" | ");
        }
        CliDisplayer.getDisplayer().displayText("\nPut the id of the player:\n>>>");
    }

    /**
     * Handles the input of the client. If the input is ok calls the method which prints the WindowFrame of the requested player.
     * @param input the input of the client in the terminal.
     * @throws InvalidInput if the input isn't between zero and four.
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
                if (nextInt < 0 || nextInt > 4) {
                    throw new InvalidInput("Wrong Input\n");
                }
                CliDisplayer.getDisplayer().printState(CliState.getCliState().getCliPlayerState(nextInt).getName());
                CliApp.getCliApp().setCurrentState(new MenuPhase());
            }
    }

    /**
     * Reset the SelectingPlayerWindowFrame's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new SelectingPlayerWindowFrame.
     */
    @Override
    public CliPhaseState reset() {
        return new SelectingPlayerWindowFrame();
    }
}
