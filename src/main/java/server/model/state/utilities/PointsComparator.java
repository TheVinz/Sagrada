package server.model.state.utilities;

import server.model.state.player.Player;
import server.model.state.player.Points;

import java.util.Comparator;

/**
 * The <tt>PointsComparator</tt> class is useful to write the final scoreboard. Compares the points of the players.
 */
public class PointsComparator implements Comparator<Player> {
    /**Given two player compares their point and return an int that indicates who's got the better score.
     * In case of tie....
     * @param o1 player 1.
     * @param o2 player 2.
     * @return an int..
     */
    @Override
    public int compare(Player o1, Player o2) {
        Points p1 = o1.getPoints();
        Points p2 = o2.getPoints();
        if(p1.getFinalPoints() != p2.getFinalPoints())
            return p2.getFinalPoints()-p1.getFinalPoints();
        else if(p1.getPointsFromPrivateCard() != p2.getPointsFromPrivateCard())
            return p2.getPointsFromPrivateCard() -p1.getPointsFromPrivateCard();
        else
            return p2.getPointsFromFavorTokens()- p1.getPointsFromFavorTokens();
    }
}
