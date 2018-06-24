package common.viewchangement;

import common.Changer;

/**
 * The <tt>LoadPlayers</tt> class contains data about every {@link server.model.state.player.Player} into a game.
 */
public class LoadPlayers extends Changement {
    private final String[] names;
    private final int[] ids;
    private final String[] windowFrameReps;
    private final int[] windowFrameFavorTokens;

    /**
     * Creates a new <tt>LoadPlayers</tt> changement relative to a new game.
     * @param names the names of the players of a game.
     * @param ids the ids of the players of a game.
     * @param windowFrameReps the initial {@link server.model.state.boards.windowframe.WindowFrame} of every player.
     * @param windowFrameFavorTokens the initial FavorTokens of every player.
     */
    public LoadPlayers(String[] names, int[] ids, String[] windowFrameReps, int[] windowFrameFavorTokens){
        this.names = names;

        this.ids = ids;
        this.windowFrameReps = windowFrameReps;
        this.windowFrameFavorTokens = windowFrameFavorTokens;
    }

    /**
     * Gets the names of the player of a game.
     * @return the names of the player of a game.
     */
    public String[] getNames() {
        return names;
    }

    /**
     * Gets the ids of the player of a game.
     * @return the ids of the player of a game.
     */
    public int[] getIds() {
        return ids;
    }

    /**
     * Gets the initial WindowFrame of every player.
     * @return the initial WindowFrame of every player.
     */
    public String[] getWindowFrameReps() {
        return windowFrameReps;
    }

    /**
     * Gets the initial FavorTokens of every player.
     * @return the initial FavorTokens of every player.
     */
    public int[] getWindowFrameFavorTokens() {
        return windowFrameFavorTokens;
    }

    /**
     * Delegate the handling of this <tt>LoadPlayers</tt> Changement to a specific {@link common.Changer}.
     * @param changer will handle this <tt>LoadPlayers.</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
