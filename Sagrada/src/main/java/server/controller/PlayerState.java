package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.state.ModelObject.ModelObject;
import server.model.Model;
import server.model.state.player.Player;

public abstract class PlayerState {

    protected Model model;
    protected Player player;

    public PlayerState(Player player, Model model){
        this.player=player;
        this.model=model;
    }

    abstract PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException, WrongParameter;
    public Response nextParam(){
        return null;
    }
}
