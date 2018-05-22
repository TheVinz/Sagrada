package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingToolCard implements CliPhaseState {

    RemoteController remoteController;
    Response nextParameter=Response.TOOL_CARD;

    public SelectingToolCard(RemoteController remoteController) {
        this.remoteController=remoteController;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        switch (nextParameter){
            case TOOL_CARD:
                return handleToolCard(input);
        }
        return new MenuPhase(remoteController);
    }

    private CliPhaseState handleToolCard(String input) throws RemoteException {
        try (Scanner sc = new Scanner(input)) {
            int index;
            index = sc.nextInt();
            remoteController.command(Response.TOOL_CARD, index);
        }
        catch (InvalidMoveException e) {
            CliDisplayer.getDisplayer().displayText(e.getMessage());
            return new MenuPhase(remoteController);
        }
        if(nextParameter==Response.END_TURN) return new MenuPhase(remoteController);
        else {
            return this;
        }
    }


}
