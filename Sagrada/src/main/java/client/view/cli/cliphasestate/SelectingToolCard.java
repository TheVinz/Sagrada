package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;


import static common.ModelObject.*;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingToolCard implements CliPhaseState {

    RemoteController remoteController;
    private CliApp cliApp;

    public SelectingToolCard(RemoteController remoteController, CliApp cliApp) {
        this.remoteController=remoteController;
        this.cliApp = cliApp;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        int choice;
        try (Scanner sc = new Scanner(input)) {
            choice = sc.nextInt();
        }
        switch (cliApp.getNextParam()){
            case TOOL_CARD:
                try{
                    cliApp.setNextParam(remoteController.command(cliApp.getNextParam(), choice));
                    break;
                }
                catch (InvalidMoveException e){
                    CliDisplayer.getDisplayer().displayText(e.getMessage());
                    return  new MenuPhase(remoteController, cliApp);
                }
            case WINDOW_FRAME_CELL:
                new SelectingWindowFrameCell(remoteController, cliApp).handle(input);
                break;
            case DRAFT_POOL_CELL:
                new SelectingDraftPoolCell(remoteController, cliApp).handle(input);
                break;
            case ROUND_TRACK_CELL:
                new SelectingDraftPoolCell(remoteController, cliApp).handle(input);
                break;
            default:
                CliDisplayer.getDisplayer().displayText("Tool card successfully used!");
                return new MenuPhase(remoteController, cliApp);
        }
        return this;
    }
}
