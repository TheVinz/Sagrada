package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;

import common.command.GameCommand;
import common.response.Response;


/**
 *  The <tt>MenuPhase</tt> class implements the method which prints to the client the Menu of the game which represents all
 *  the possible input that the client can put and that are going to be processed.
 */
public class MenuPhase implements CliPhaseState{


    /**
     * Prints the menu to the client.
     */
    public MenuPhase(){
        CliDisplayer.getDisplayer().printMenu();
        CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");

    }

    /**
     * Handles the input of the client.
     * @param input the input of the client.
     * @throws InvalidInput
     */
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
                CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");
                break;
            case "S":
                CliApp.getCliApp().setCurrentState(new SelectingPlayerWindowFrame());
                CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");
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

    /**
     * Reset the MenuPhase's {@link client.view.cli.cliphasestate.CliPhaseState}.
     * @return a new MenuPhase.
     */
    @Override
    public CliPhaseState reset() {
        return new MenuPhase();
    }


}
