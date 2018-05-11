package client.view.cli.cliphasestate;

import client.view.cli.CliController;
import client.view.cli.CliDisplayer;
import common.CommandVisitor;
import common.command.GameCommand;
import common.response.Response;

public class MovingDicePhase implements CliPhaseSate {
    private CliDisplayer cliDisplayer;
    private CommandVisitor commandVisitor;
    private boolean firstCell;

    public MovingDicePhase(CliDisplayer cliDisplayer, CommandVisitor commandVisitor) {

        this.cliDisplayer = cliDisplayer;
        this.commandVisitor = commandVisitor;
        this.firstCell = false;
    }

    @Override
    public CliPhaseSate handle(String input) {
        Response response;
        if(!firstCell)
            if(input.length()==0 && input.charAt(0)>=0 && input.charAt(0)<=9)
                response = commandVisitor.handle(new GameCommand(1, 0));
                
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
