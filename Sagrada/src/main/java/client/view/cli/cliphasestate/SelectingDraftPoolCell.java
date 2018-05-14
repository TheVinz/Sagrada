package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingDraftPoolCell implements CliPhaseState {

    RemoteController remoteController;

    public SelectingDraftPoolCell(RemoteController remoteController) {
        this.remoteController=remoteController;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        try(Scanner sc = new Scanner(input)){
            int index;
            index=sc.nextInt();
            remoteController.command(ModelObject.DRAFT_POOL_CELL, index);
        } catch (InvalidMoveException e) {
            CliDisplayer.getDisplayer().displayText(e.getMessage());
        }
        return new MenuPhase(remoteController);
    }
}
