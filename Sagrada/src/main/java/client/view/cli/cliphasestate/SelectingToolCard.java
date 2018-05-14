package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingToolCard implements CliPhaseState {

    RemoteController remoteController;

    public SelectingToolCard(RemoteController remoteController) {
        this.remoteController=remoteController;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        try (Scanner sc = new Scanner(input)) {
            int index;
            index = sc.nextInt();
            remoteController.command(ModelObject.TOOL_CARD, index);
        }
        catch (InvalidMoveException e) {
            CliDisplayer.getDisplayer().displayText(e.getMessage());
        }
        return new MenuPhase(remoteController);
    }
}
