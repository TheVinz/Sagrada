package server.controller;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import common.ModelObject;
import server.model.state.player.Player;

public class WaitingState extends PlayerState {

    private PlayerState nextState;

    public WaitingState(Player player, Model model){
        super(player, model);
    }

    @Override
    public PlayerState selectObject(ModelObject  modelObject) throws InvalidMoveException {
        switch (modelObject.getType()){
            case ModelObject.DRAFT_POOL_CELL:
                if(!player.isDiceMoved()) {
                    nextState = new MovingDice(player, model);
                    return nextState.selectObject(modelObject);
                }
                else return this;
            case ModelObject.TOOL_CARD:
                if(!player.isToolCardUsed()) {
                    nextState = new UsingToolCard(player, model);
                    return nextState.selectObject(modelObject);
                }
                else return this;
            default:
                return this;
        }
    }

    @Override
    public int nextParam() {
        return -1;
    }


}
