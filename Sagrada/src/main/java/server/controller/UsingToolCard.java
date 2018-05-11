package server.controller;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.controller.PlayerState;
import server.state.ModelObject;
import server.state.boards.windowframe.WindowFrame;
import server.state.player.Player;
import server.state.toolcards.ToolCard;

public class UsingToolCard implements PlayerState {

    private Player player;
    private Model model;
    private ToolCard card;

    public UsingToolCard(Player player, Model model){
        this.model=model;
        this.player=player;
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
}