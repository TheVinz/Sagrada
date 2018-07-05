package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.player.Player;
import server.model.state.toolcards.SinglePlayerToolCard;
import server.model.state.toolcards.ToolCard;


/**
 * The <tt>UsingToolCard</tt> is the {@link PlayerState} handling all the {@link ToolCard}'s effect a player can use.
 */
public class UsingToolCard extends PlayerState {

    private ToolCard card;
    private Response nextParameter = null;

    /**
     * Initializes a new <tt>UsingToolCard</tt> setting the {@link Model} of the current game and the {@link Player} related
     * to the actions represented by this state.
     * @param player the player associated to this state.
     * @param model the model of the current game.
     */
    public UsingToolCard(Player player, Model model){
        super(player, model);
    }

    /**
     * Set the next ToolCard parameter with the {@link ModelObject} representing the client's input. If the modelObject is
     * a ToolCard then it is initialized (if the Player has enough favor tokens) otherwise the object is sent to the tool card.
     * @param modelObject the object representing the client input.
     * @return <code>this</code> if the tool card effect is not resolved yet, a new {@link WaitingState} in case of
     * successful use of the tool card's effect.
     * @throws InvalidMoveException if this exception is thrown by the card or if the player does not have enough tokens
     * (multi-player) or the card has already been used (single-player).
     * @throws WrongParameter if this exception is thrown by the tool card.
     */
    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException, WrongParameter {
        ModelType i = modelObject.getType();
        if (i == ModelType.TOOL_CARD) {
            if(!model.isSingleplayer()) {
                if (card == null)
                    card = (ToolCard) modelObject;
                if (card.isUsed())
                    if (player.getFavorTokens() < 2)
                        throw new InvalidMoveException("Not enough favor tokens");
                else if (player.getFavorTokens() < 1)
                    throw new InvalidMoveException("Not enough favor tokens");
                card.start(player);
            }
            else{
                ToolCard toolCard = (ToolCard) modelObject;
                if(toolCard.isUsed())
                    throw new InvalidMoveException("This card has already been used");
                card = new SinglePlayerToolCard(model);
                card.start(player);
                card.setParameter(toolCard);
            }
            if (card.hasNext())
                return this;
            else
            {
                nextParameter = Response.SUCCESS_USED_TOOL_CARD;
                player.getTimer().start();
                return new WaitingState(player, model);
            }
        } else {
            card.setParameter(modelObject);
            if (card.hasNext())
                return this;
            else
            {
                nextParameter = Response.SUCCESS_USED_TOOL_CARD;
                player.getTimer().start();
                return new WaitingState(player, model);
            }
        }
    }

    /**
     * Returns the next move the client is required to perform.
     * @return the next move the client is required to perform. If the tool card effect is resolved null is returned.
     */
    @Override
    public Response nextParam() {
        if(nextParameter == null){
            if(card.hasNext()) {
                return card.next();
            }
            else return null;
        }else{
            return nextParameter;
        }

    }

    /**
     * Aborts the tool card's effect.
     */
    @Override
    public void abort(){
        card.abort();
    }
}
