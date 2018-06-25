package common.viewchangement;

import common.Changer;

/**
 * The <tt>ToolCardUsed</tt> class contains the data to send to the client about a {@link server.model.state.toolcards.ToolCard}
 * that has been used.
 */
public class ToolCardUsed extends Changement {
    private final int id;
    private final int index;
    private final int tokens;

    /**
     * Creates a new <tt>ToolCardUsed</tt> changement relative to a ToolCard that has been used.
     * @param id the id of the {@link server.model.state.player.Player} that used the ToolCard.
     * @param index the index in the ToolCards' array of the ToolCard used.
     * @param tokens an int which indicates the required FavorTokens to use the ToolCard.
     */
    public ToolCardUsed(int id, int index, int tokens){
        this.id = id;
        this.index = index;
        this.tokens = tokens;
    }

    /**
     * Gets the id of the Player that used the ToolCard.
     * @return the id of the Player that used the ToolCard.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the index in the ToolCards' array of the ToolCard used.
     * @return the index in the ToolCards' array of the ToolCard used.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets an int which indicates the required FavorTokens to use the ToolCard.
     * @return an int which indicates the required FavorTokens to use the ToolCard.
     */
    public int getTokens() {
        return tokens;
    }

    /**
     * Delegate the handling of this <tt>ToolCardUsed</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>ToolCardUsed</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
