package common.viewchangement;

import common.Changer;
import common.response.Response;

/**
 * The <tt>RemoveDice</tt> class contains the data to send to the client about a removed {@link server.model.state.dice.Dice}
 * by a {@link server.model.state.player.Player}.
 */
public class RemovedDice extends Changement{
    private final int id;
    private final Response type;
    private final int index;

    /**
     * Creates a new <tt>RemovedDice</tt> changement relative to a removed Dice.
     * @param id the id of the player which removed the Dice.
     * @param type the type of the {@link server.model.state.boards.Cell} where was the Dice.
     * @param index an int which indicates the position of the cell in the {@link server.model.state.boards.draftpool.DraftPool}.
     */
    public RemovedDice(int id, Response type, int index){

        this.id = id;
        this.type = type;
        this.index = index;
    }

    /**
     * Gets the id of the player which removed the Dice.
     * @return the id of the player which removed the Dice.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the type of the Cell where was the Dice.
     * @return the type of the Cell where was the Dice.
     */
    public Response getType() {
        return type;
    }

    /**
     * Gets an int which indicates the position of the cell in the DraftPool.
     * @return an int which indicates the position of the cell in the DraftPool.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Delegate the handling of this <tt>RemovedDice</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>RemovedDice</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
