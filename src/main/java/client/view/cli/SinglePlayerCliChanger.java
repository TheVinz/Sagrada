package client.view.cli;

import common.viewchangement.ToolCardUsed;

import java.rmi.RemoteException;

public class SinglePlayerCliChanger extends CliChanger {
    public SinglePlayerCliChanger() {
        super();
        CliDisplayer.getDisplayer().setSinglePlayer(true);
    }

    @Override
    public void change(ToolCardUsed toolCardUsed) {
        CliPlayerState playerState=CliState.getCliState().getCliPlayerState(toolCardUsed.getId());
        CliDisplayer.getDisplayer().displayText("You used tool card No. " + CliState.getCliState().getToolCardIds()[toolCardUsed.getIndex()] + "\n");
        CliState.getCliState().getToolCardUsed()[toolCardUsed.getIndex()] = true;
    }
}
