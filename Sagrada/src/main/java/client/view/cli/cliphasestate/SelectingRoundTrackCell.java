package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingRoundTrackCell implements CliPhaseState {

    RemoteController remoteController;
    private CliApp cliApp;

    public SelectingRoundTrackCell(RemoteController remoteController, CliApp cliApp) {
        this.remoteController=remoteController;
        this.cliApp = cliApp;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        try(Scanner sc = new Scanner(input)){
            int round, index;
            round=sc.nextInt();
            index=sc.nextInt();
            cliApp.setNextParam(remoteController.command(ModelObject.ROUND_TRACK_CELL, round, index));
        }catch(InvalidMoveException e){
            CliDisplayer.getDisplayer().displayText(e.getMessage());
        }
        return new MenuPhase(remoteController);
    }
}
