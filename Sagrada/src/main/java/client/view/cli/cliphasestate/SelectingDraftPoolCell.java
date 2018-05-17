package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingDraftPoolCell implements CliPhaseState {

    RemoteController remoteController;
    private CliApp cliApp;

    public SelectingDraftPoolCell(RemoteController remoteController, CliApp cliApp) {
        this.remoteController=remoteController;
        this.cliApp = cliApp;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        try(Scanner sc = new Scanner(input)){
            int index;
            index=sc.nextInt();
            cliApp.setNextParam(remoteController.command(ModelObject.DRAFT_POOL_CELL, index));
        } catch (InvalidMoveException e) {
            CliDisplayer.getDisplayer().displayText(e.getMessage() + "\n>>>");
            return new MenuPhase(remoteController);
        }
        return new MenuPhase(remoteController);
    }
}
