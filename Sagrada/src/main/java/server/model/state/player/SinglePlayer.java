package server.model.state.player;

import server.model.state.State;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;

public class SinglePlayer extends Player {
    public SinglePlayer(String name, int id) {
        super(name, id);
    }

    @Override
    public void calculatePoints(State state) {

    }
}
