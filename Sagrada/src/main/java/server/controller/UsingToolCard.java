package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.player.Player;
import server.model.state.toolcards.ToolCard;


public class UsingToolCard extends PlayerState {

    private ToolCard card;
    private Response nextParameter = null;

    public UsingToolCard(Player player, Model model){
        super(player, model);
    }

    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException, WrongParameter {
        ModelType i = modelObject.getType();
        if (i == ModelType.TOOL_CARD) {
            if (card == null) card = (ToolCard) modelObject;
            if(card.isUsed())
                if(player.getFavorTokens() <2)
                    throw new InvalidMoveException("Not enough favor tokens");
            else if(player.getFavorTokens()<1)
                    throw new InvalidMoveException("Not enough favor tokens");
            card.start(player);
            if (card.hasNext())
                return this;
            else
            {
                nextParameter = Response.SUCCESS_USED_TOOL_CARD;
                return new WaitingState(player, model);
            }
        } else {
            card.setParameter(modelObject);
            if (card.hasNext())
                return this;
            else
            {
                nextParameter = Response.SUCCESS_USED_TOOL_CARD;
                return new WaitingState(player, model);
            }
        }
    }

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
}
