package server.model.state.utilities;

import server.model.state.player.Player;
import server.model.state.player.Points;

import java.util.Comparator;

public class PointsComparator implements Comparator<Player> {
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

            //ultimo caso lo farà gabriele
    }
}
