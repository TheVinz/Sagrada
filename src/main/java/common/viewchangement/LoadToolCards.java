package common.viewchangement;


import common.Changer;

/**
 * The <tt>LoadToolCards</tt> class contains data to send to the client about the {@link server.model.state.toolcards.ToolCard} in a game.
 */
public class LoadToolCards extends Changement {

    private final int[] toolCards;

    /**
     * Creates a new <tt>LoadToolCards</tt> changement relative to the ToolCards in a game.
     * @param toolCards an array of int which represents the ToolCards.
     */
    public LoadToolCards(int[] toolCards){
        this.toolCards=toolCards;
    }


    /**
     * Gets the ToolCards in a game.
     * @return an array con int which represents the ToolCards.
     */
    public int[] getToolCards() {
        return toolCards;
    }

    /**
     * Delegate the handling of this <tt>LoadToolCards</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>LoadToolCards</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
