package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.response.Response;
import server.model.state.ModelObject.ModelObject;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingWindowFrameCell implements CliPhaseState {

    private int row;

    public SelectingWindowFrameCell() {
        this.row = -1;
        CliDisplayer.getDisplayer().displayText("Select the first coordinate of the WindowFrame: ");
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

            if (row == -1) {
                if (nextInt < 0 || nextInt > 3)
                    throw new InvalidInput("Wrong Input\n");
                else{
                    row = nextInt;
                    CliDisplayer.getDisplayer().displayText("Select the second coordinate of the WindowFrame: ");
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

    @Override
    public CliPhaseState reset() {
        return new SelectingWindowFrameCell();
    }
}
