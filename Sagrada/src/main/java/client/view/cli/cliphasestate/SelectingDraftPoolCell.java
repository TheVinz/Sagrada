package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.CliState;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectingDraftPoolCell implements CliPhaseState {



    public SelectingDraftPoolCell() {
        CliDisplayer.getDisplayer().displayText("Select the number of the cell: ");
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
            if(nextInt < 0 || nextInt >= CliState.getCliState().getDraftPool().length)
                throw new InvalidInput("Wrong Input\n");

                CliApp.getCliApp().addCommandToBuffer(new GameCommand(ModelObject.DRAFT_POOL_CELL, nextInt));
        }

    }

    @Override
    public CliPhaseState reset() {
        return new SelectingDraftPoolCell();

    }
}
