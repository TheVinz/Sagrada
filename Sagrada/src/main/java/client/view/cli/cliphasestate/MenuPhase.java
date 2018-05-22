package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import client.view.cli.SynchronizedObserver;
import common.command.GameCommand;

import static common.command.GameCommand.END_TURN;



public class MenuPhase implements CliPhaseState, SynchronizedObserver {


    public MenuPhase(){
        CliDisplayer.getDisplayer().printMenu();
        CliDisplayer.getDisplayer().displayText("Insert an option:\n>>>");

    }

    @Override
    synchronized public void handle(String input) throws InvalidInput {

        switch (input){

            case "M":
                CliDisplayer.getDisplayer().printMenu();
                break;
            case "P":
                CliDisplayer.getDisplayer().printDraftPool();
                break;
            case "C":
                CliDisplayer.getDisplayer().printPrivateObjectiveCard();
                break;
            case "F":
                CliDisplayer.getDisplayer().printFavorTokens();
                break;
            case "V":
                CliDisplayer.getDisplayer().printState();
                break;
            case "T":
                CliDisplayer.getDisplayer().printToolCard();
                break;
            case "O":
                CliDisplayer.getDisplayer().printPublicObjectiveCards();
                break;
            case "R":
                CliDisplayer.getDisplayer().printRoundTrack();
                break;
            case "S":
                CliApp.getCliApp().setCurrentState(new SelectingSendingPlayerWindowFrame());
                break;
            case "D":
                CliApp.getCliApp().setSynchronizedObserver(this);
                CliApp.getCliApp().setCurrentState(new SelectingDraftPoolCell());
                while(CliApp.getCliApp().getCommandBufferSize() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("error\n");
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setCurrentState(new SelectingWindowFrameCell());
                CliApp.getCliApp().sendCommand();
                break;

            case "U":
                CliDisplayer.getDisplayer().printToolCard();
                CliApp.getCliApp().setCurrentState(new SelectingSendingToolCard());
                break;

            case "N":
                CliApp.getCliApp().addCommandToBuffer(new GameCommand(END_TURN));
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

    @Override
    synchronized public void notifyThis() {
        this.notifyAll();
    }
}
