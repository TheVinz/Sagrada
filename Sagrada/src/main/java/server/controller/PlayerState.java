package server.controller;

import common.exceptions.InvalidMoveException;
import server.model.state.ModelObject;

public interface PlayerState {

    PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException;
}
