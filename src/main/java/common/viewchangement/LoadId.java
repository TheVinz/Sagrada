package common.viewchangement;

import common.Changer;

/**
 * The <tt>LoadId</tt> class contains data to send to the client to give him an id.
 */
public class LoadId extends Changement {
    private final int id;

    /**
     * Creates a new <tt>LoadId</tt> changement relative to an id of a {@link server.model.state.player.Player}.
     * @param id of a player.
     */
    public LoadId(int id){

        this.id = id;
    }

    /**
     * Gets the id of a player.
     * @return id of a player.
     */
    public int getId() {
        return id;
    }

    /**
     * Delegate the handling of this <tt>LoadId</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>LoadId</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
