package client.view.cli.cliphasestate;

import java.rmi.RemoteException;

public interface CliPhaseSate {
    CliPhaseSate handle(String input) throws RemoteException;
}
