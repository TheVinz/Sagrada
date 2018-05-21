package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingWindowFrameCell implements CliPhaseState {

    RemoteController remoteController;
    private CliApp cliApp;
    private int row;

    public SelectingWindowFrameCell(RemoteController remoteController, CliApp cliApp) {

        this.remoteController = remoteController;
        this.cliApp = cliApp;
        this.row = -1;
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

            if (row == -1) {
                if (nextInt < 0 || nextInt > 3)
                    CliDisplayer.getDisplayer().displayText("Wrong Input, try again: ");
                else
                    row = nextInt;
                return this;
            } else {
                if (nextInt < 0 || nextInt > 4) {
                    CliDisplayer.getDisplayer().displayText("Wrong Input, try again: ");
                    return this;
                } else {
                    try {
                        cliApp.setNextParam(remoteController.command(ModelObject.WINDOW_FRAME_CELL, row, nextInt));
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
