package server.controller;

import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.player.Player;
import server.model.state.toolcards.ToolCard;


public class UsingToolCard extends PlayerState {

    private ToolCard card;

    public UsingToolCard(Player player, Model model){
        super(player, model);
    }

    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException {
        ModelType i = modelObject.getType();
        if (i == ModelType.TOOL_CARD) {
            if (card == null) card = (ToolCard) modelObject;
            card.start(player);
            return this;
        } else {
            card.setParameter(modelObject);
            if (card.hasNext())
                return this;
            else return new WaitingState(player, model);
        }
    }

    @Override
    public Response nextParam() {
        if(card.hasNext())
            return card.next();
        else return null;
    }
}
