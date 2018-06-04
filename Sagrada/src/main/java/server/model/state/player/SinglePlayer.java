package server.model.state.player;

import server.model.state.State;

public class SinglePlayer extends Player {
    public SinglePlayer(String name, int id) {
        super(name, id);
    }
    @Override
    public void calculatePoints(State state) {
        Points points =super.getPoints();
        for (int card=0; card<2; card++) {
            points.setPointsFromPublicCard(card,state.getPublicObjectiveCards().get(card).calculatePoints(super.getWindowFrame()));
        }
        points.setPointsFromPublicCard(2, 0);
        points.setPointsFromFavorTokens(0);
        points.setPointsFromPrivateCard(super.getPrivateObjectiveCard(0).calculatePoints(super.getWindowFrame()));  //
        points.setPointsFromEmptyCells(super.getWindowFrame().getEmptyCells()*3);
    }
}
