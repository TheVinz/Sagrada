package common.viewchangement;

import common.Changer;

/**
 * The <tt>EndGame</tt> class contains data to send to the client to inform him about the scoreboard, the final points of every
 * {@link server.model.state.player.Player}.
 */
public class EndGame extends Changement {
    private final char[] charCards;
    private final int[] scoreboardIds;
    private final int[][] matrixPoins;

    /**
     * Creates a new <tt>EndGame</tt> changement relative to the final points of the players in a game.
     * @param charCards an array with the {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard } of the players in the game.
     * @param scoreboardIds an array with player's id order by the final scoreboard.
     * @param matrixPoins a matrix that indicates how a player received the points.
     */
    public EndGame(char[] charCards, int[] scoreboardIds, int[][] matrixPoins){

        this.charCards = charCards;
        this.scoreboardIds = scoreboardIds;
        this.matrixPoins = matrixPoins;
    }


    /**
     * Gets the PrivateObjectiveCard of every player in the game.
     * @return charCards an array with the PrivateObjectiveCard of the players in the game.
     */
    public char[] getCharCards() {
        return charCards;
    }

    /**
     * Gets the ids of the player ordered by the final scoreboard.
     * @return scoreboardIds an array with player's id ordered by the final scoreboard.
     */
    public int[] getScoreboardIds() {
        return scoreboardIds;
    }

    /**
     * Gets the points from ObjectiveCards, FavorTokens,EmptyCell and the total points of every player.
     * @return matrixPoins a matrix that indicates how a player received the points.
     */
    public int[][] getMatrixPoins() {
        return matrixPoins;
    }

    /**
     ** Delegate the handling of this <tt>EndGame</tt> Changement to a specific {@link common.Changer}.
     *  @param changer will handle this <tt>EndGame</tt> Changement.
     */
    public void change(Changer changer){
        changer.change(this);
    }
}
