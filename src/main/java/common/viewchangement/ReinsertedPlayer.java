package common.viewchangement;

import common.Changer;

/**
 * The <tt>ReinsertedPlayer</tt> class contains the data to send to the client about the {@link server.model.state.player.Player} whose is going to be
 * reinserted in the game.
 */
public class ReinsertedPlayer extends Changement {
    private final int idPlayer;

    /**
     * Creates a new <tt>ReinsertedPlayer</tt> changement relative to a Player whose is going to be reinserted in the game.
     * @param idPlayer the id of the Player which is going to be reinserted in the game.
     */
    public ReinsertedPlayer(int idPlayer){

        this.idPlayer = idPlayer;
    }

    /**
     * Gets the id of the Player which is going to be reinserted in the game.
     * @return the id of the Player which is going to be reinserted in the game.
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * Delegate the handling of this <tt>ReinsertedPlayer</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>ReinsertedPlayer</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
