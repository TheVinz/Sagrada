package client.view.cli;

import common.ChangementVisitor;
import common.CommandVisitor;

public class CliController {
    CommandVisitor commandVisitor;
    CliDisplayer cliDisplayer;
    public CliController(CommandVisitor commandVisitor, CliDisplayer cliDisplayer){
        this.commandVisitor = commandVisitor;
        this.cliDisplayer = cliDisplayer;
    }
}
