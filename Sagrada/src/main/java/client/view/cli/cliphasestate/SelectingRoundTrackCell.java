package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingRoundTrackCell implements CliPhaseState {

    RemoteController remoteController;
    private CliApp cliApp;
    private int round;

    public SelectingRoundTrackCell(RemoteController remoteController, CliApp cliApp) {
        this.remoteController=remoteController;
        this.cliApp = cliApp;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        try (Scanner sc = new Scanner(input)) {
            int nextInt = -1;
            try {
                nextInt = sc.nextInt();
            } catch (InputMismatchException e) {
                CliDisplayer.getDisplayer().displayText("Wrong Input, try again: ");
                return this;
            }

            if (round == -1) {
                if (//)
                    CliDisplayer.getDisplayer().displayText("Wrong Input, try again: ");
                else
                    round = nextInt;
                return this;
            } else {
                if (//) {
                    CliDisplayer.getDisplayer().displayText("Wrong Input, try again: ");
                    return this;
                } else {
                    try {
                        cliApp.setNextParam(remoteController.command(ModelObject.ROUND_TRACK_CELL, round, nextInt));
                        return null;
                    } catch (InvalidMoveException e) {
                        CliDisplayer.getDisplayer().displayText(e.getMessage() + "\n>>>");
                        return new MenuPhase(remoteController, cliApp);
                    }
                }
            }

        }
    }
}
}
