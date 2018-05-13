package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.CommandVisitor;
import common.command.GameCommand;
import common.response.Response;

import java.rmi.RemoteException;

public class MenuPhase implements CliPhaseSate {
    private CliDisplayer cliDisplayer;
    private CommandVisitor commandVisitor;

    public MenuPhase(CliDisplayer cliDisplayer, CommandVisitor commandVisitor){
        this.cliDisplayer = cliDisplayer;
        this.commandVisitor = commandVisitor;
    }

    @Override
    public CliPhaseSate handle(String input) throws RemoteException {
        switch (input){

            case "M":
                cliDisplayer.printMenu();
                return this;
            case "P":
                cliDisplayer.printDraftPool();
                return this;
            case "V":
               // cliDisplayer.printWindowFrame();     //mi serve il giocatore
            case "T":
                cliDisplayer.printToolCard();
            case "O":
                cliDisplayer.printPublicObjectiveCards();
            case "A":
                cliDisplayer.displayText("Put the name of the player:\n");
            case "D":
                cliDisplayer.printDraftPool();
                cliDisplayer.displayText("Select a draft pool cell:\n ");
                return new MovingDicePhase(cliDisplayer, commandVisitor);
            case "U":
                cliDisplayer.printToolCard();
                cliDisplayer.displayText("Select a tool card: ");
                return  new UsingToolCardPhase(cliDisplayer, commandVisitor);
            case "N":
                Response response = commandVisitor.handle(new GameCommand(GameCommand.END_TURN));
                if(response.getType() != Response.SUCCESS)
                    cliDisplayer.displayText("Something wrong!\n");
                return this;
            default:
                cliDisplayer.displayText("Input error\n");
                return this;

        }
    }
}
