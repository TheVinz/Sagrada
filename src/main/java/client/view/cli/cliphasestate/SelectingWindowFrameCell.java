package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliState;
import common.response.Response;
import server.model.state.ModelObject.ModelObject;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>SelectingWindowFrameCell</tt> class implements the method which handles the choice of a {@link server.model.state.boards.Cell}
 * in the {@link server.model.state.boards.windowframe.WindowFrame} by the client.
 */
public class SelectingWindowFrameCell implements CliPhaseState {

    private int row;

    /**
     * Asks the client to select the row of the Cell.
     */
    public SelectingWindowFrameCell() {
        this.row = -1;
        CliDisplayer.getDisplayer().displayText("Select the first coordinate of the WindowFrame:\n>>> ");
    }

    /**
     * Handles the input of the client. If the row is between zero and three asks the column of the Cell, if it isn't
     * throw a new {@link client.view.cli.cliphasestate.InvalidInput}.
     * If the inputs are ok calls the method which allows to continue the move.
     * @param input the input of the client.
     * @throws InvalidInput if the row isn't between zero and three and if the column isn't between zero and four.
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

            if (row == -1) {
                if (nextInt < 0 || nextInt > 3)
                    throw new InvalidInput("Wrong Input\n");
                else{
                    row = nextInt;
                    CliDisplayer.getDisplayer().displayText("Select the second coordinate of the WindowFrame:\n>>> ");
                }
            } else {
                if (nextInt < 0 || nextInt > 4) {
                    throw new InvalidInput("Wrong Input\n");
                } else {
                    CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.WINDOW_FRAME_CELL, row, nextInt));
                }
            }

        }
    }

    /**
     * Reset the SelectingWindowFrameCell's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new SelectingWindowFrameCell.
     */
    @Override
    public CliPhaseState reset() {
        return new SelectingWindowFrameCell();
    }
}
