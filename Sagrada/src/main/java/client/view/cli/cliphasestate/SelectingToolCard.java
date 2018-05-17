package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.state.boards.roundtrack.RoundTrackCell;

import static common.ModelObject.*;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingToolCard implements CliPhaseState {

    RemoteController remoteController;
    private CliApp cliApp;
    int nextParameter = -1;

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
        switch (nextParameter){
            case TOOL_CARD:
                try{
                    nextParameter = remoteController.command(nextParameter, choice);
                    return this;
                    //stampo il prossimo parametro
                }
                catch (InvalidMoveException e){
                    CliDisplayer.getDisplayer().displayText(e.getMessage());
                    return  new MenuPhase(remoteController);
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
                return new MenuPhase(remoteController);
        }
        nextParameter = cliApp.getNextParam();
        return this;



    }






}
