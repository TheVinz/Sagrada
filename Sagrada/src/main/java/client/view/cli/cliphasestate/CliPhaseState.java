package client.view.cli.cliphasestate;

import java.rmi.RemoteException;

public interface CliPhaseState {
    CliPhaseState handle(String input) throws RemoteException;
}
