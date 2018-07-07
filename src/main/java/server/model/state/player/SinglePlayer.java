package server.model.state.player;

import server.model.state.State;

/**
 * The <tt>SinglePlayer</tt> class extends {@link Player} and is used to manage the remote client's state for single-player games.
 */
public class SinglePlayer extends Player {
    /**
     * Initializes the <tt>SinglePlayer</tt> with the given username and id.
     * @param name the player's username.
     * @param id the player's id.
     */
    public SinglePlayer(String name, int id) {
        super(name, id);
    }

    /**
     * Calculate this player's points.
     * @param state the game's {@link State}.
     */
    @Override
    public void calculatePoints(State state) {
        Points points =super.getPoints();
        for (int card=0; card<2; card++) {
            points.setPointsFromPublicCard(card,state.getPublicObjectiveCards().get(card).calculatePoints(super.getWindowFrame()));
        }
        points.setPointsFromPublicCard(2, 0);
        points.setPointsFromFavorTokens(0);
        points.setPointsFromPrivateCard(super.getPrivateObjectiveCard(0).calculatePoints(super.getWindowFrame()));
        points.setPointsFromEmptyCells(super.getWindowFrame().getEmptyCells()*3);
    }
}
