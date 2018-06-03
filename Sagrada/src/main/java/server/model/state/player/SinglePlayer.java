package server.model.state.player;

import server.model.state.State;

public class SinglePlayer extends Player {
    private PointsSinglePlayer points;
    public SinglePlayer(String name, int id) {
        super(name, id);
    }
    @Override
    public void calculatePoints(State state) {
        for (int card=0; card<2; card++) {
            points.setPointsFromPublicCard(card,state.getPublicObjectiveCards().get(card).calculatePoints(super.getWindowFrame()));
        }
        points.setPointsFromPrivateCard(super.getPrivateObjectiveCard(0).calculatePoints(super.getWindowFrame()));  //
        points.setPointsFromEmptyCells(super.getWindowFrame().getEmptyCells());
        points.setObjectivePoints(state.getRoundTrack().calculatePoints());
    }
    public PointsSinglePlayer getSinglePlayerPoints(){
        return points;
    }
}
