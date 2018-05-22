package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
<<<<<<< HEAD
import common.response.Response;
=======
import client.view.cli.CliState;
>>>>>>> 2c7dab538083ec4475f1370256f5bf8635536588
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
<<<<<<< HEAD
    public CliPhaseState handle(String input) throws RemoteException {
        try(Scanner sc = new Scanner(input)){
            int index;
            index=sc.nextInt();
            remoteController.command(Response.DRAFT_POOL_CELL, index);
        } catch (InvalidMoveException e) {
            CliDisplayer.getDisplayer().displayText(e.getMessage() + "\n>>>");
            return new MenuPhase(remoteController);
=======
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
>>>>>>> 2c7dab538083ec4475f1370256f5bf8635536588
        }

    }

    @Override
    public CliPhaseState reset() {
        return new SelectingDraftPoolCell();

    }
}
