package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
<<<<<<< HEAD
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.controller.MovingDice;

import java.rmi.RemoteException;
=======
import client.view.cli.SynchronizedObserver;
import common.command.GameCommand;
>>>>>>> 2c7dab538083ec4475f1370256f5bf8635536588

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
                CliApp.getCliApp().setCurrentState(new SelectingDraftPoolCell());
                CliApp.getCliApp().setSynchronizedObserver(this);
                while(CliApp.getCliApp().getCommandBufferSize() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setCurrentState(new SelectingWindowFrameCell());
                CliApp.getCliApp().sendCommand();
                break;

            case "U":
                CliDisplayer.getDisplayer().printToolCard();
                CliApp.getCliApp().setCurrentState(new SelectingSendingToolCard());
                break;

            case "N":
<<<<<<< HEAD
                try {
                    remoteController.command(Response.END_TURN);
                } catch (InvalidMoveException e) {
                    CliDisplayer.getDisplayer().displayText(e.getMessage() + "\n>>>");
                }
                return this;
=======
                CliApp.getCliApp().addCommandToBuffer(new GameCommand(END_TURN));
                CliApp.getCliApp().sendCommand();
                CliApp.getCliApp().setWaitingPhase(true);
                break;
>>>>>>> 2c7dab538083ec4475f1370256f5bf8635536588
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
