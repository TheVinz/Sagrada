package client.view.cli.cliphasestate;

import client.view.cli.CliController;
import client.view.cli.CliDisplayer;

public class UsingToolCardPhase implements CliPhaseSate {
    private CliDisplayer cliDisplayer;
    private CliController cliController;

    public UsingToolCardPhase(CliDisplayer cliDisplayer, CliController cliController) {

        this.cliDisplayer = cliDisplayer;
        this.cliController = cliController;
    }

    @Override
    public CliPhaseSate handle(String input) {
        switch (input){
            case "P":
                //
                break;
            case "M":
                cliDisplayer.printMenu();
                break;
            //code missing
            default:
                cliDisplayer.displayText("Input error");

        }
        return this;

    }
}
