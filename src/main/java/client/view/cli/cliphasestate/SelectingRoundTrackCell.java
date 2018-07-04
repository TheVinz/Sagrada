package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;

import client.view.cli.CliState;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <tt>SelectingRoundTrackCell</tt> class implements the method which handles the choice of a {@link server.model.state.boards.Cell}
 * in the {@link server.model.state.boards.roundtrack.RoundTrack} by the client.
 */
public class SelectingRoundTrackCell implements CliPhaseState {

    private int round;

    /**
     * Asks the client to select the round where there is the desired Cell.
     */
    public SelectingRoundTrackCell() {
        CliDisplayer.getDisplayer().displayText("\nSelect the round of the RoundTrack:\n>>> ");
        round = -1;
    }

    /**
     * Handles the first input of the client which should be a valid round, if it is asks the client the position of the Cell,
     * it it isn't throws a new InvalidInput exception. If the inputs are ok calls the method which allows to continue the move.
     * @param input the input of the client.
     * @throws InvalidInput if the round doesn't exist and if the position of the cell given a round isn't correct.
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

            if (round == -1) {
                if (nextInt >= CliState.getCliState().getRound()|| nextInt < 1)
                    throw new InvalidInput("Wrong Input (current round is "+CliState.getCliState().getRound()+"\n");
                else {
                    CliDisplayer.getDisplayer().displayText("Select the number of the cell:\n>>> ");
                    round = nextInt;
                }
            } else {
                if (nextInt < 0 || nextInt >= CliState.getCliState().getRoundTrack()[round-1].length) {
                    throw new InvalidInput("Wrong Input\n");
                } else
                    CliApp.getCliApp().addCommandToBuffer( new GameCommand(Response.ROUND_TRACK_CELL, round, nextInt));

                }
            }

        }

    /**
     * Reset the SelectingRoundTrackCell's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new SelectingRoundTrackCell.
     */
    @Override
    public CliPhaseState reset() {
        return new SelectingRoundTrackCell();
    }
    }


