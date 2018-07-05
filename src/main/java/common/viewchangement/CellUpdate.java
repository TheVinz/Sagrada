package common.viewchangement;


import common.Changer;
import common.response.Response;

/**
 *The <tt>CellUpdate</tt> class contains data to send to the client to inform him about the update of a {@link server.model.state.boards.Cell}.
 */
public class CellUpdate extends Changement {


    private final int playerId;
    private final Response cellType;
    private final int row;
    private final int column;
    private final int value;
    private final char color;

    /**
     * Creates a new <tt>CellUpdate</tt> changement relative to a {@link server.model.state.boards.windowframe.WindowFrame} or a {@link server.model.state.boards.roundtrack.RoundTrack}.
     * @param player the id of the {@link server.model.state.player.Player}, in whose board the cell has been updated.
     * @param type indicates in which part of the board the cell has been updated.
     * @param row first index to localize the cell.
     * @param column second index to localize the cell.
     * @param value the value of the {@link server.model.state.dice.Dice} (zero for no Dice).
     * @param color the {@link server.model.state.utilities.Color} of the Dice.
     */
    public CellUpdate(int player, Response type, int row, int column, int value, char color) {
        this.playerId = player;
        this.cellType=type;
        this.row=row;
        this.column=column;
        this.value=value;
        this.color=color;
    }

    /**
     * Creates a new <tt>CellUpdate</tt> changement relative to a {@link server.model.state.boards.draftpool.DraftPool}.
     * @param player the id of the player, in whose board the cell has been updated.
     * @param type indicates in which part of the board the cell has been updated.
     * @param row first index to localize the cell.
     * @param value the value of the dice (zero for no dice).
     * @param color color the color of the dice.
     */
    public CellUpdate(int player, Response type, int row, int value, char color) {
        this.playerId = player;
        this.cellType=type;
        this.row=row;
        this.column=-1;
        this.value=value;
        this.color=color;
    }


    /**
     * Get the id of the player, in whose board the cell has been updated.
     * @return the id of the player, in whose board the cell has been updated.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Get the <tt>Response</tt> that indicates in which part of the board the cell has been updated.
     * @return the <tt>Response</tt> that indicates in which part of the board the cell has been updated.
     */
    public Response getCellType() {
        return cellType;
    }

    /**
     * Get first index to localize the cell.
     * @return first index to localize the cell.
     */
    public int getRow() {
        return row;
    }

    /**
     * Get first index to localize the cell.
     * @return first index to localize the cell.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Get the value of the dice (zero for no dice).
     * @return the value of the dice (zero for no dice).
     */
    public int getValue() {
        return value;
    }

    /**
     * Get color the color of the dice.
     * @return color the color of the dice.
     */
    public char getColor() {
        return color;
    }

    /**
     * Delegate the handling of this <tt>UpdateCell</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>UpdateCell</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }

}
