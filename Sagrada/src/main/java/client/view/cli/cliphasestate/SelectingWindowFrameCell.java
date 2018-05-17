package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingWindowFrameCell implements CliPhaseState {

    RemoteController remoteController;
    private CliApp cliApp;

    public SelectingWindowFrameCell(RemoteController remoteController, CliApp cliApp) {
        this.remoteController=remoteController;
        this.cliApp = cliApp;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        try (Scanner sc = new Scanner(input)) {
            int row, column;
            row=sc.nextInt();
            column=sc.nextInt();
            cliApp.setNextParam(remoteController.command(ModelObject.WINDOW_FRAME_CELL, row, column));
        }
        catch (InvalidMoveException e){
            CliDisplayer.getDisplayer().displayText(e.getMessage() + "\n>>>");
            return new MenuPhase(remoteController);
        }
        return new MenuPhase(remoteController);
    }
}
