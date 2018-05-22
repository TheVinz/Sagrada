package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliState;
import common.ModelObject;
import common.command.GameCommand;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingRoundTrackCell implements CliPhaseState {

    private int round;

    public SelectingRoundTrackCell() {
        CliDisplayer.getDisplayer().displayText("Select the round of the RoundTrack: ");
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

            if (round == -1) {
                if (nextInt >= CliState.getCliState().getCurrentRound() || nextInt < 0)
                    throw new InvalidInput("Wrong Input\n");
                else {
                    CliDisplayer.getDisplayer().displayText("Select the number of the cell: ");
                    round = nextInt;
                }
            } else {
                if (nextInt < 0 || nextInt >= CliState.getCliState().getRoundTrack()[round].length) {
                    throw new InvalidInput("Wrong Input\n");
                } else
                    CliApp.getCliApp().addCommandToBuffer( new GameCommand(ModelObject.ROUND_TRACK_CELL, round, nextInt));

                }
            }

        }
    @Override
    public CliPhaseState reset() {
        return new SelectingRoundTrackCell();
    }
    }


