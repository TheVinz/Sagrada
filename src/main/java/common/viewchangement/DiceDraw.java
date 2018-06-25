package common.viewchangement;

import common.Changer;

/**
 * The <tt>DiceDraw</tt> class contains data to send to the client to inform him about the update of a {@link server.model.state.dice.Dice}.
 */
public class DiceDraw extends Changement {
    private final int id;
    private final char color;

    /**
     * Creates a new <tt>DiceDraw</tt> changement relative to a Dice.
     * @param id the id of the {@link server.model.state.player.Player} that drawn the Dice.
     * @param color of the dice drawn.
     */
    public DiceDraw(int id, char color){

        this.id = id;
        this.color = color;
    }

    /**
     * Gets the id of the Player that drawn the Dice.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Get color the color of the Dice.
     * @return color the color of the Dice.
     */
    public char getColor() {
        return color;
    }

    /**
     ** Delegate the handling of this <tt>DiceDraw</tt> Changement to a specific {@link common.Changer}.
     *  @param changer will handle this <tt>DiceDraw</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
