package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;

import common.command.GameCommand;
import common.response.Response;


public class MenuPhase implements CliPhaseState{


    public MenuPhase(){
        CliDisplayer.getDisplayer().printMenu();
        CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");

    }

    @Override
    synchronized public void handle(String input) throws InvalidInput {

        switch (input){

            case "M":
                CliDisplayer.getDisplayer().printMenu();
                CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");
                break;
            case "P":
                CliDisplayer.getDisplayer().printDraftPool();
                CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");
                break;
            case "V":
                CliDisplayer.getDisplayer().printState();
                CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");
                break;
            case "T":
                CliDisplayer.getDisplayer().printToolCard();
                CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");
                break;
            case "O":
                CliDisplayer.getDisplayer().printPublicObjectiveCards();
                CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");
                break;
            case "R":
                CliDisplayer.getDisplayer().printRoundTrack();
                break;
            case "S":
                CliApp.getCliApp().setCurrentState(new SelectingPlayerWindowFrame());
                break;
            case "D":
                CliApp.getCliApp().addCommandToBuffer( new GameCommand(Response.SIMPLE_MOVE_REQUEST));
                CliApp.getCliApp().sendCommand();
                break;

            case "U":
                CliDisplayer.getDisplayer().printToolCard();
                CliApp.getCliApp().setCurrentState(new SelectingSendingToolCard());
                break;

            case "N":

                CliApp.getCliApp().addCommandToBuffer(new GameCommand(Response.END_TURN));
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
            default:
                CliDisplayer.getDisplayer().displayText("Input error\n");
        }
    }

    @Override
    public CliPhaseState reset() {
        return new MenuPhase();
    }


}
