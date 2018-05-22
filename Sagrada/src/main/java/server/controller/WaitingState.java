package server.controller;

import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.player.Player;

public class WaitingState extends PlayerState {

    private PlayerState nextState;

    public WaitingState(Player player, Model model){
        super(player, model);
    }

    @Override
    public PlayerState selectObject(ModelObject  modelObject) throws InvalidMoveException {
        switch (modelObject.getType()){
            case DRAFT_POOL_CELL:
                if(!player.isDiceMoved()) {
                    nextState = new MovingDice(player, model);
                    return nextState.selectObject(modelObject);
                }
                else return this;
            case TOOL_CARD:
                if(!player.isToolCardUsed()) {
                    nextState = new UsingToolCard(player, model);
                    return nextState.selectObject(modelObject);
                }
                else return this;
            default:
                return this;
        }
    }



}
