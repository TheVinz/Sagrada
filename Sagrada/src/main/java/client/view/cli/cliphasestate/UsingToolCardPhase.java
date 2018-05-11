package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.CommandVisitor;

import java.rmi.RemoteException;

public class UsingToolCardPhase implements CliPhaseSate {
    private CliDisplayer cliDisplayer;
    private CommandVisitor commandVisitor;

    public UsingToolCardPhase(CliDisplayer cliDisplayer, CommandVisitor commandVisitor) {

        this.cliDisplayer = cliDisplayer;
        this.commandVisitor = commandVisitor;
    }

    @Override
    public CliPhaseSate handle(String input) throws RemoteException {

        return this;

    }
}
