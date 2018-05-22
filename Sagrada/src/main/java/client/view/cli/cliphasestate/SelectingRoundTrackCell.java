package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.response.Response;
import server.model.state.ModelObject.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingRoundTrackCell implements CliPhaseState {

    RemoteController remoteController;

    public SelectingRoundTrackCell(RemoteController remoteController) {
        this.remoteController=remoteController;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        try(Scanner sc = new Scanner(input)){
            int round, index;
            round=sc.nextInt();
            index=sc.nextInt();
            remoteController.command(Response.ROUND_TRACK_CELL, round, index);
        }catch(InvalidMoveException e){
            CliDisplayer.getDisplayer().displayText(e.getMessage());
        }
        return new MenuPhase(remoteController);
    }
}
