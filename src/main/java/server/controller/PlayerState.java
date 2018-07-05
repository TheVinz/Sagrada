package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.state.ModelObject.ModelObject;
import server.model.Model;
import server.model.state.player.Player;

/**
 * The <tt>PlayerState</tt> is the abstract class for all the state class for the implementation of a state pattern in
 * the controller.
 */
public abstract class PlayerState {

    protected Model model;
    protected Player player;

    /**
     * Initializes a new <tt>PlayerState</tt> setting the {@link Model} of the current game and the {@link Player} related
     * to the actions represented by this state.
     * @param player the player associated to this state.
     * @param model the model of the current game.
     */
    public PlayerState(Player player, Model model){
        this.player=player;
        this.model=model;
    }

    /**
     * Translates the object selected by the client into a move depending on the subclass.
     * @param modelObject the object representing the client input.
     * @return the next game phase.
     * @throws InvalidMoveException if the client is trying to perform a not valid move, aborting it.
     * @throws WrongParameter if the client is trying to perform a not valid move, without aborting it.
     */
    abstract PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException, WrongParameter;

    /**
     * Returns the next move the client is required to perform. <code>null</code> if the player is not the current active player.
     * @return <code>null</code>.
     */
    public Response nextParam(){
        return null;
    }

    /**
     * Aborts the current action the player is performing.
     */
    public void abort(){}
}
