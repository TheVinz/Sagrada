package client.view.cli;

import common.viewchangement.Changement;
import common.viewchangement.ToolCardUsed;

import java.rmi.RemoteException;

/**
 * The class <tt>SinglePlayerCliChanger</tt> implements Override the {@link ToolCardUsed} {@link Changement} method in CliChanger.
 * Handles the case of a used {@link server.model.state.toolcards.ToolCard} in the SinglePlayer mode.
 */
public class SinglePlayerCliChanger extends CliChanger {
    private static  SinglePlayerCliChanger singlePlayerCliChanger;

    /**
     * Initializes the SinglePlayerCliChanger class and set to true in {@link CliDisplayer} the boolean which indicates if it is a SinglePlayer game.
     */
    private SinglePlayerCliChanger() {
        super();
        CliDisplayer.getDisplayer().setSinglePlayer(true);
    }

    /**
     * Returns the SinglePlayerCliChanger class
     * @return the SinglePlayerCliChanger class.
     */
    public static SinglePlayerCliChanger getSinglePlayerCliChanger() {
        if(singlePlayerCliChanger == null)
            singlePlayerCliChanger = new SinglePlayerCliChanger();
        return singlePlayerCliChanger;
    }

    /**
     * Marks in {@link CliState} if a ToolCard has ben used, because a player in SinglePlayer mode cannot reuse a used ToolCard.
     * @param toolCardUsed the Changement containing information about the used ToolCard.
     */
    @Override
    public void change(ToolCardUsed toolCardUsed) {
        CliPlayerState playerState=CliState.getCliState().getCliPlayerState(toolCardUsed.getId());
        CliDisplayer.getDisplayer().displayText("You used tool card No. " + CliState.getCliState().getToolCardIds()[toolCardUsed.getIndex()] + "\n");
        CliState.getCliState().getToolCardUsed()[toolCardUsed.getIndex()] = true;
    }
}
