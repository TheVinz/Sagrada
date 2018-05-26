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
        round = -1;
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
                if (nextInt >= CliState.getCliState().getRound()|| nextInt < 1)
                    throw new InvalidInput("Wrong Input (current round is "+CliState.getCliState().getRound()+"\n");
                else {
                    CliDisplayer.getDisplayer().displayText("Select the number of the cell:\n>>> ");
                    round = nextInt;
                }
            } else {
                if (nextInt < 0 || nextInt >= CliState.getCliState().getRoundTrack()[round-1].length/2) {
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


