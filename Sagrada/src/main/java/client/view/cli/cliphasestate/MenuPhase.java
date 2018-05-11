package client.view.cli.cliphasestate;

import client.view.cli.CliController;
import client.view.cli.CliDisplayer;

public class MenuPhase implements CliPhaseSate {
    boolean movedDice;
    private CliDisplayer cliDisplayer;

    public MenuPhase(CliDisplayer cliDisplayer, CliController cliController){
        this.cliDisplayer = cliDisplayer;
    }

    @Override
    public CliPhaseSate handle(String input) {
        cliDisplayer.displayText("Inserisci un comando: ");
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
