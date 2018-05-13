package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.CommandVisitor;
import common.command.GameCommand;
import common.response.Response;

import java.rmi.RemoteException;

import static common.command.GameCommand.WINDOW_FRAME_CLICK;
import static common.response.Response.*;

public class MovingDicePhase implements CliPhaseSate {
    private CliDisplayer cliDisplayer;
    private CommandVisitor commandVisitor;
    private boolean firstCell;
    private Integer firstCoordinate;


    public MovingDicePhase(CliDisplayer cliDisplayer, CommandVisitor commandVisitor) {

        this.cliDisplayer = cliDisplayer;
        this.commandVisitor = commandVisitor;
        this.firstCell = false;
        this.firstCoordinate = null;
    }

    @Override
    public CliPhaseSate handle(String input) throws RemoteException {
        if(input.length()==1 && input.charAt(0)=='M'){
            cliDisplayer.printMenu();
            return new MenuPhase(cliDisplayer, commandVisitor);
        }
        Response response;

        if(!firstCell) {
            if (input.length() == 0 && input.charAt(0) >= 0 && input.charAt(0) <= 9) {
                response = commandVisitor.handle(new GameCommand(1, 0));
                switch (response.getType()) {
                    case WINDOW_FRAME_CLICK:
                        firstCell = true;
                        cliDisplayer.printPlayerWindowFrame();
                        cliDisplayer.displayText("Select the first coordinate: ");
                        return this;
                    case WRONG_PARAMETER:
                        cliDisplayer.displayText(response.getMessage());
                        return new MenuPhase(cliDisplayer, commandVisitor);
                    default:
                        cliDisplayer.displayText("Try Again: ");
                        return new MenuPhase(cliDisplayer, commandVisitor);
                }
            }
            else{
                cliDisplayer.displayText("Wrong Input, try again: ");
                return this;
            }

        }
        else{

            if(firstCoordinate == null)
            {
                if (input.length() == 0 && input.charAt(0) >= 0 && input.charAt(0) <= 9) {
                    firstCoordinate = input.charAt(0) - '0';
                    cliDisplayer.displayText("Select the second coordinate: ");
                    return this;
                }
                else{
                    cliDisplayer.displayText("Wrong Input, try again: ");
                    return this;
                }
            }
            else{
                if (input.length() == 0 && input.charAt(0) >= 0 && input.charAt(0) <= 9) {
                    response = commandVisitor.handle(new GameCommand(WINDOW_FRAME_CLICK, firstCoordinate, input.charAt(0)-'0'));
                    switch (response.getType()){
                        case WRONG_PARAMETER:
                            cliDisplayer.displayText(response.getMessage());
                            return new MenuPhase(cliDisplayer, commandVisitor);
                        case SUCCESS:
                            cliDisplayer.displayText("OK");
                            return new MenuPhase(cliDisplayer, commandVisitor);
                        default:
                            cliDisplayer.displayText("Try Again: ");
                            return new MenuPhase(cliDisplayer, commandVisitor);
                    }
                }
                else{
                    cliDisplayer.displayText("Wrong Input, try again: ");
                    return this;
                }
            }

        }
    }
}
