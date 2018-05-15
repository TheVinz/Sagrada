package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.ModelObject;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;
import common.response.Response;

import static common.ModelObject.*;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SelectingToolCard implements CliPhaseState {

    RemoteController remoteController;
    int nextParameter=TOOL_CARD;

    public SelectingToolCard(RemoteController remoteController) {
        this.remoteController=remoteController;
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        switch (nextParameter){
            case TOOL_CARD:
                return handleToolCard(input);
        }
        return new MenuPhase(remoteController);
    }

    private CliPhaseState handleToolCard(String input) throws RemoteException {
        try (Scanner sc = new Scanner(input)) {
            int index;
            index = sc.nextInt();
            nextParameter=remoteController.command(ModelObject.TOOL_CARD, index);
        }
        catch (InvalidMoveException e) {
            CliDisplayer.getDisplayer().displayText(e.getMessage());
            return new MenuPhase(remoteController);
        }
        if(nextParameter==Response.END) return new MenuPhase(remoteController);
        else {
            printMessage();
            return this;
        }
    }

    private void printMessage() {
        switch(nextParameter){
            case WINDOW_FRAME:
            case ROUND_TRACK_CELL:
            case DRAFT_POOL_CELL:
            case CHOICE:
        }
    }


}
