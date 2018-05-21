package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import server.controller.MovingDice;

import java.rmi.RemoteException;

import static common.command.GameCommand.END_TURN;
import static common.command.GameCommand.USE_TOOL_CARD;

public class MenuPhase implements CliPhaseState {

    private RemoteController remoteController;
    private CliApp cliApp;

    public MenuPhase(RemoteController remoteController, CliApp cliApp){
        this.remoteController = remoteController;
        this.cliApp = cliApp;
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
            case "C":
                CliDisplayer.getDisplayer().printPrivateObjectiveCard();
                return this;
            case "F":
                CliDisplayer.getDisplayer().printFavorTokens();
                return  this;
            case "V":
                CliDisplayer.getDisplayer().printState();
                return this;
            case "T":
                CliDisplayer.getDisplayer().printToolCard();
                return this;
            case "O":
                CliDisplayer.getDisplayer().printPublicObjectiveCards();
                return this;
            case "R":
                CliDisplayer.getDisplayer().printRoundTrack();
                return this;
            case "S":
                CliDisplayer.getDisplayer().displayText("Put the name of the player:\n");
                return new PrintingWindowFramePhase(remoteController);
            case "D":
                return new MovingDicePhase(remoteController, cliApp);
            case "U":
                CliDisplayer.getDisplayer().printToolCard();
                cliApp.setNextParam(USE_TOOL_CARD);
                return new SelectingToolCard(remoteController, cliApp);

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
