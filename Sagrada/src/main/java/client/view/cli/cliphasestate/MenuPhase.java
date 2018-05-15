package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import server.controller.MovingDice;

import java.rmi.RemoteException;

import static common.command.GameCommand.END_TURN;

public class MenuPhase implements CliPhaseState {

    private RemoteController remoteController;

    public MenuPhase(RemoteController remoteController){
        this.remoteController = remoteController;
        }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {

        switch (input){

            case "M":
                CliDisplayer.getDisplayer().printMenu();
                return this;
            case "P":
                CliDisplayer.getDisplayer().printDraftPool();
                return this;
            case "V":
                CliDisplayer.getDisplayer().printWindowFrame();
                return this;
            case "T":
                CliDisplayer.getDisplayer().printToolCard();
                return this;
            case "O":
                CliDisplayer.getDisplayer().printPublicObjectiveCards();
                return this;
            case "A":
                CliDisplayer.getDisplayer().displayText("Put the name of the player:\n");
                return new PrintingWindowFramePhase(remoteController);
            case "D":
                return new MovingDicePhase(remoteController);
            case "U":
                CliDisplayer.getDisplayer().printToolCard();
                return new SelectingToolCard(remoteController);
            case "W":
                CliDisplayer.getDisplayer().printWindowFrame();
                CliDisplayer.getDisplayer().displayText("Select a dice from the window frame: \n");
                return new SelectingWindowFrameCell(remoteController);
            case "R":
                CliDisplayer.getDisplayer().printRoundTrack();
                CliDisplayer.getDisplayer().displayText("Select a dice from the round track:\n");
                return new SelectingRoundTrackCell(remoteController);
            case "N":
                try {
                    remoteController.command(END_TURN);
                } catch (InvalidMoveException e) {
                    CliDisplayer.getDisplayer().displayText(e.getMessage() + "\n>>>");
                }
                return this;
            default:
                CliDisplayer.getDisplayer().displayText("Input error\n");
                return this;

        }
    }
}
