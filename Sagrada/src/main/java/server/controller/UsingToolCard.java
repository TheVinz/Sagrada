package server.controller;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import common.ModelObject;
import server.model.state.player.Player;
import server.model.state.toolcards.ToolCard;


public class UsingToolCard extends PlayerState {

    private ToolCard card;

    public UsingToolCard(Player player, Model model){
        super(player, model);
    }

    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException {
        int i = modelObject.getType();
        if (i == ModelObject.TOOL_CARD) {
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
    public int nextParam() {
        return card.next();
    }
}
