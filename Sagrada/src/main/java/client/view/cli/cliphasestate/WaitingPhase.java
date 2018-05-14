package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import client.view.cli.cliphasestate.CliPhaseState;

import java.rmi.RemoteException;

public class WaitingPhase implements CliPhaseState {

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        CliDisplayer.getDisplayer().displayText("Wait, is not your turn...");
        return this;
    }
}
