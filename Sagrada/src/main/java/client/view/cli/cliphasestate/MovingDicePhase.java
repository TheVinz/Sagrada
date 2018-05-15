package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;

public class MovingDicePhase implements CliPhaseState {

    private RemoteController remoteController;
    private boolean draftPool=true;

    MovingDicePhase(RemoteController remoteController){
        this.remoteController=remoteController;
        CliDisplayer.getDisplayer().printDraftPool();
        CliDisplayer.getDisplayer().displayText("Select a dice from the draft pool\n>>>");
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        if(draftPool) {
            new SelectingDraftPoolCell(remoteController).handle(input);
            draftPool=false;
            CliDisplayer.getDisplayer().printWindowFrame();
            CliDisplayer.getDisplayer().displayText("Select an empty space from your own window frame\n>>>");
            return this;
        }
        else{
            new SelectingWindowFrameCell(remoteController).handle(input);
            return new MenuPhase(remoteController);
        }
    }
}
