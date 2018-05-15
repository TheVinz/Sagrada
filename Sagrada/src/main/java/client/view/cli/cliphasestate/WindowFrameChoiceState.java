package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;

public class WindowFrameChoiceState implements CliPhaseState {

    RemoteController remoteController;


    public WindowFrameChoiceState(RemoteController remoteController) {
        this.remoteController=remoteController;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        int choice = Integer.parseInt(input);
        try {
            remoteController.command(ModelObject.CHOICE, choice);
        } catch (InvalidMoveException e) {
            CliDisplayer.getDisplayer().displayText(e.getMessage());
            return this;
        }
        return new WaitingPhase();
    }
}
