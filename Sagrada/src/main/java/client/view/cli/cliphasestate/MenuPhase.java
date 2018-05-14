package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.RemoteMVC.RemoteController;
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
               // CliDisplayer.getDisplayer().printWindowFrame();     //mi serve il giocatore
            case "T":
                CliDisplayer.getDisplayer().printToolCard();
                return this;
            case "O":
                CliDisplayer.getDisplayer().printPublicObjectiveCards();
                return this;
            case "A":
                CliDisplayer.getDisplayer().displayText("Put the name of the player:\n");
                return this;
            case "D":
                CliDisplayer.getDisplayer().printDraftPool();
                CliDisplayer.getDisplayer().displayText("Select a draft pool dice:\n ");
                return new SelectingDraftPoolCell(remoteController);
            case "U":
                CliDisplayer.getDisplayer().printToolCard();
                CliDisplayer.getDisplayer().displayText("Select a tool card: \n");
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
                remoteController.command(END_TURN);
                return this;
            default:
                CliDisplayer.getDisplayer().displayText("Input error\n");
                return this;

        }
    }
}
