package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;

import client.view.cli.CliState;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *  The <tt>SelectingDraftPoolCell</tt> class implements the method which handles the choice of a {@link server.model.state.boards.Cell}
 *  in the {@link server.model.state.boards.draftpool.DraftPool} by the client.
 */
public class SelectingDraftPoolCell implements CliPhaseState {


    /**
     * Asks the client to select a Cell of the DraftPool.
     */
    public SelectingDraftPoolCell() {
        CliDisplayer.getDisplayer().displayText("Select the number of the cell:\n>>> ");
    }

    /**
     * Handles the input of the client. If the input is ok calls the method which allows to continue the move.
     * @param input the input of the client in the terminal.
     * @throws InvalidInput if the input isn't between zero and the DraftPool length.
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
            if(nextInt < 0 || nextInt >= CliState.getCliState().getDraftPool().length)
                throw new InvalidInput("Wrong Input\n");

                CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.DRAFT_POOL_CELL, nextInt));
        }

    }

    /**
     * Reset the SelectingDraftPoolCell's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new SelectingDraftPoolCell.
     */
    @Override
    public CliPhaseState reset() {
        return new SelectingDraftPoolCell();

    }
}
