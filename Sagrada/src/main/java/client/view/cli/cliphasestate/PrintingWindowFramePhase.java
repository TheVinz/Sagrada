package client.view.cli.cliphasestate;

import client.view.cli.CliDisplayer;
import common.RemoteMVC.RemoteController;

import java.rmi.RemoteException;

public class PrintingWindowFramePhase implements CliPhaseState {

    private RemoteController remoteController;

    public PrintingWindowFramePhase(RemoteController remoteController){
        this.remoteController=remoteController;
    }

    @Override
    public CliPhaseState handle(String input) {
        CliDisplayer.getDisplayer().printPlayerWindowFrame(input);
        return new MenuPhase(remoteController);
    }
}
