package common.viewchangement;

import common.Changer;

/**
 * The <tt>SuspendedPlayer</tt> class contains the data to send to the client
 * about the {@link server.model.state.player.Player} which is going to be
 * suspended in the game.
 */
public class SuspendedPlayer extends Changement {
    private final int playerId;

    /**
     * Creates a new <tt>SuspendedPlayer</tt> changement relative to a Player whose is going to be suspended in the game
     * @param playerId the id of the Player which is going to be suspended in the game.
     */
    public SuspendedPlayer(int playerId){

        this.playerId = playerId;
    }

    /**
     * Gets the id of the Player which is going to be suspended in the game.
     * @return the id of the Player which is going to be suspended in the game.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Delegate the handling of this <tt>SuspendedPlayer</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>SuspendedPlayer</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
