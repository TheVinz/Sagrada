package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;

import client.view.cli.CliState;
import common.command.GameCommand;
import common.response.Response;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingRoundTrackCell implements CliPhaseState {

    private int round;

    public SelectingRoundTrackCell() {
        CliDisplayer.getDisplayer().displayText("\nSelect the round of the RoundTrack:\n>>> ");
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
                    CliDisplayer.getDisplayer().displayText("Select the number of the cell:\n>>> ");
                    round = nextInt;
                }
            } else {
                if (nextInt < 0 || nextInt >= CliState.getCliState().getRoundTrack()[round].length) {
                    throw new InvalidInput("Wrong Input\n");
                } else
                    CliApp.getCliApp().addCommandToBuffer( new GameCommand(Response.ROUND_TRACK_CELL, round, nextInt));

                }
            }

        }
    @Override
    public CliPhaseState reset() {
        return new SelectingRoundTrackCell();
    }
    }


