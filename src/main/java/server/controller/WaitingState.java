package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.player.Player;

public class WaitingState extends PlayerState {

    private PlayerState nextState;
    private Response nextParameter = null;

    public WaitingState(Player player, Model model){
        super(player, model);
    }

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

    @Override
    public Response nextParam(){
        return nextParameter;
    }



}
