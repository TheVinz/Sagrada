package client.view.cli;

import common.viewchangement.ToolCardUsed;

import java.rmi.RemoteException;

public class SinglePlayerCliChanger extends CliChanger {
    private static  SinglePlayerCliChanger singlePlayerCliChanger;

    private SinglePlayerCliChanger() {
        super();
        CliDisplayer.getDisplayer().setSinglePlayer(true);
    }

    public static SinglePlayerCliChanger getSinglePlayerCliChanger() {
        if(singlePlayerCliChanger == null)
            singlePlayerCliChanger = new SinglePlayerCliChanger();
        return singlePlayerCliChanger;
    }

    @Override
    public void change(ToolCardUsed toolCardUsed) {
        CliPlayerState playerState=CliState.getCliState().getCliPlayerState(toolCardUsed.getId());
        CliDisplayer.getDisplayer().displayText("You used tool card No. " + CliState.getCliState().getToolCardIds()[toolCardUsed.getIndex()] + "\n");
        CliState.getCliState().getToolCardUsed()[toolCardUsed.getIndex()] = true;
    }
}
