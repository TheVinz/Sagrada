package common.viewchangement;

import common.Changer;

/**
 * The <tt>NewTurn</tt> class contains the data to send to the client about the id of the {@link server.model.state.player.Player} whose going to play.
 */
public class NewTurn extends Changement {
    private final int id;

    /**
     * Creates a new <tt>NewTurn</tt> changement relative to a new turn.
     * @param id the id of the player whose going to play.
     */
    public NewTurn(int id){

        this.id = id;
    }

    /**
     * Gets the id of the player whose going to play.
     * @return an int that represents the id of the player whose going to play.
     */
    public int getId() {
        return id;
    }

    /**
     * Delegate the handling of this <tt>NewTurn</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>NewTurn</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
