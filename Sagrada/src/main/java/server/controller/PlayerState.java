package server.controller;

import common.exceptions.InvalidMoveException;
import common.ModelObject;
import server.model.Model;
import server.model.state.player.Player;

public abstract class PlayerState {

    protected Model model;
    protected Player player;

    public PlayerState(Player player, Model model){
        this.player=player;
        this.model=model;
    }

    abstract PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException;
    abstract int nextParam();
}
