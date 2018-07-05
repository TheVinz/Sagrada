package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.player.Player;

/**
 * The <tt>WaitingState</tt> is the {@link PlayerState} representing the main phase of the player turn. in this state
 * the client can either start a dice move or a {@link server.model.state.toolcards.ToolCard}'s effect.
 */
public class WaitingState extends PlayerState {

    private PlayerState nextState;
    private Response nextParameter = null;

    /**
     * Initializes a new <tt>WaitingState</tt> setting the {@link Model} of the current game and the {@link Player} related
     * to the actions represented by this state.
     * @param player the player associated to this state.
     * @param model the model of the current game.
     */
    public WaitingState(Player player, Model model){
        super(player, model);
    }

    /**
     * Returns a new {@link MovingDice} if the modelObject is a {@link server.model.state.boards.draftpool.DraftPoolCell} and
     * the player can perform a dice move or a new {@link UsingToolCard} if the modelObject is a {@link server.model.state.toolcards.ToolCard}
     * and the player can use it. Otherwise <code>this</code> is returned.
     * @param modelObject the object representing the client input.
     * @return a new MovingDice if the player wants to start a new dice move, a new UsingToolCard if the player wants to use a
     * tool card effect or <code>this</code>.
     * @throws InvalidMoveException if the dice move or the tool card use cannot be performed.
     * @throws WrongParameter if the dice move or the tool card's effect throw it.
     */
    @Override
    public PlayerState selectObject(ModelObject  modelObject) throws InvalidMoveException, WrongParameter {
        switch (modelObject.getType()){
            case DRAFT_POOL_CELL:
                if(!player.isDiceMoved()) {
                    nextState = new MovingDice(player, model);
                    return nextState.selectObject(modelObject);
                }
                else{
                    throw new InvalidMoveException("You have already moved a dice!\n");
                }
            case TOOL_CARD:
                if(!player.isToolCardUsed()) {
                    nextState = new UsingToolCard(player, model);
                    PlayerState temp = nextState.selectObject(modelObject);
                    nextParameter = nextState.nextParam();
                    return temp;
                }
                else{
                        throw new InvalidMoveException("You have already used a tool card!\n");
                    }
            default:
                return this;
        }
    }

    /**
     * Returns the next move the client is required to perform.
     * @return the next move the client is required to perform.
     */
    @Override
    public Response nextParam(){
        return nextParameter;
    }



}
