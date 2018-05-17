package client.view.cli.cliphasestate;

import client.view.cli.CliApp;
import client.view.cli.CliDisplayer;
import common.RemoteMVC.RemoteController;
import common.exceptions.InvalidMoveException;

import java.rmi.RemoteException;

public class MovingDicePhase implements CliPhaseState {

    private RemoteController remoteController;
    private CliApp cliApp;
    private boolean draftPool=true;

    MovingDicePhase(RemoteController remoteController, CliApp cliApp){
        this.remoteController=remoteController;
        this.cliApp = cliApp;
        CliDisplayer.getDisplayer().printDraftPool();
        CliDisplayer.getDisplayer().displayText("Select a dice from the draft pool\n>>>");
    }

    @Override
    public CliPhaseState handle(String input) throws RemoteException {
        if(draftPool) {
            new SelectingDraftPoolCell(remoteController, cliApp).handle(input);
            draftPool=false;
            CliDisplayer.getDisplayer().printWindowFrame();
            CliDisplayer.getDisplayer().displayText("Select an empty space from your own window frame\n>>>");
            return this;
        }
        else{
            new SelectingWindowFrameCell(remoteController, cliApp).handle(input);
            return new MenuPhase(remoteController);
        }
    }
}
