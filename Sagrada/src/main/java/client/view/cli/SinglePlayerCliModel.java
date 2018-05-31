package client.view.cli;

import java.rmi.RemoteException;

public class SinglePlayerCliModel extends CliModel {
    public SinglePlayerCliModel() throws RemoteException {
        super();
        CliDisplayer.getDisplayer().setSinglePlayer(true);
    }

    @Override
    public void toolCardUsed(int player, int index, int tokens) {
        CliPlayerState playerState=CliState.getCliState().getCliPlayerState(player);
        CliDisplayer.getDisplayer().displayText("You used tool card No. " + CliState.getCliState().getToolCardIds()[index] + "\n");
    }
}
