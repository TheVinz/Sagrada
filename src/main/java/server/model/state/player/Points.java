package server.model.state.player;

/**
 * The <tt>Points</tt> class represents the points scored by a player, splitting them into:
 * <ul>
 *     <li>Points from public objective cards;</li>
 *     <li>Points from private objective cards;</li>
 *     <li>Empty cells penalty;</li>
 *     <li>Favor tokens bonus.</li>
 * </ul>
 * Each {@link Player} has his own instance of the <tt>Points</tt> class.
 */
public class Points {
    private int[] pointsFromPublicCard= new int[3];
    private int pointsFromPrivateCard;
    private int pointsFromEmptyCells;
    private int pointsFromFavorTokens;


    /**
     * Sets the points given by the indicated {@link server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard} to the specified value.
     * @param card the public objective card's index.
     * @param points the points scored thanks to the card's ability.
     */
    public void setPointsFromPublicCard(int card, int points) {
        this.pointsFromPublicCard[card] = points;
    }

    /**
     * Returns the points scored thanks to the specified public objective card.
     * @param card the public objective card's index.
     * @return the points scored thanks to the specified card.
     */
    public int getPointsFromPublicCard(int card) {
       return this.pointsFromPublicCard[card];
    }

    /**
     * Returns the points scored thanks to the {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard}.
     * @return the points scored thanks to the private objective card.
     */
    public int getPointsFromPrivateCard() {
        return pointsFromPrivateCard;
    }

    /**
     * Sets the points scored thanks to the private objective card to the given value.
     * @param pointsFromPrivateCard the points scored thanks to the private objective card.
     */
    public void setPointsFromPrivateCard(int pointsFromPrivateCard) {
        this.pointsFromPrivateCard = pointsFromPrivateCard;
    }

    /**
     * Returns the points loosed because of the empty cells. This value is less than or equal to 0.
     * @return the points loosed because of the empty cells.
     */
    public int getPointsFromEmptyCells() {
        return pointsFromEmptyCells;
    }

    /**
     * Sets the value of the points loosed because of the empty {@link server.model.state.boards.windowframe.WindowFrameCell}s. This value shall be positive.
     * @param pointsFromEmptyCells the value of the points loosed because of the empty cells.
     */
    public void setPointsFromEmptyCells(int pointsFromEmptyCells) {
        this.pointsFromEmptyCells = (-1)*pointsFromEmptyCells;
    }

    /**
     * Returns the points gained thanks to the unused favor tokens.
     * @return the points gained thanks to the unused favor tokens.
     */
    public int getPointsFromFavorTokens() {
        return pointsFromFavorTokens;
    }

    /**
     * Sets the points scored thanks to the unused favor tokens to the given value.
     * @param pointsFromFavorTokens the points scored thanks to the unused favor tokens.
     */
    public void setPointsFromFavorTokens(int pointsFromFavorTokens) {
        this.pointsFromFavorTokens = pointsFromFavorTokens;
    }

    /**
     * Returns the points scored thanks to the {@link server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard}s.
     * @return the points scored thanks to the public objective cards.
     */
    public int getPointsFromPublicCards(){
        return pointsFromPublicCard[0]+pointsFromPublicCard[1]+pointsFromPublicCard[2];
    }

    /**
     * Returns the sum of all the points scored.
     * @return the total points scored.
     */
    public int getFinalPoints(){
        return getPointsFromPublicCards()+pointsFromPrivateCard+pointsFromFavorTokens+pointsFromEmptyCells;
    }
}
