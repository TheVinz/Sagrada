package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import client.view.cli.CliState;
import common.CommandVisitor;
import common.command.GameCommand;
import common.response.Response;

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
        if(input.length()==1 && input.charAt(0)=='M'){
            cliDisplayer.printMenu();
            return new MenuPhase(cliDisplayer, commandVisitor);
        }
        Response response;
        if (input.length() == 0 && input.charAt(0) >= 0 && input.charAt(0) <= 9)
            response = commandVisitor.handle(new GameCommand(4, 0));  //index sarebbe? non dovrebbe essere l'input?
        switch (input) {
            case "0":

                cliDisplayer.displayText("");
            case "1":
                cliDisplayer.displayText("Hai scelto di usare la ToolCard "  );  //serve CliState per prendere la ToolCard

            case "2":
                cliDisplayer.displayText("");
        }
        return this;

    }
}
