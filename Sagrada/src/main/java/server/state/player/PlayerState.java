package server.state.player;

import common.exceptions.InvalidMoveException;
import server.state.ModelObject;

public interface PlayerState {

    void selectObject(ModelObject modelObject) throws InvalidMoveException;
    void resetState();
}
