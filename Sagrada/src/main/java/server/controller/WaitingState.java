package server.controller;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.controller.PlayerState;
import server.state.ModelObject;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.player.Player;


public class WaitingState implements PlayerState {
    private Model model;
    private Player player;

    private PlayerState nextState;

    public WaitingState(Model model, Player player){
        this.model = model;
        this.player = player;
    }

    @Override
    public PlayerState selectObject(ModelObject  modelObject) throws InvalidMoveException {
        switch (modelObject.getType()){
            case ModelObject.DRAFT_POOL_CELL:
                if(!player.diceMoved()) {
                    nextState = new MovingDice(player, model);
                    return nextState.selectObject(modelObject);
                }
                else return this;
            case ModelObject.TOOL_CARD:
                if(!player.toolCardUsed()) {
                    nextState = new UsingToolCard(player, model);
                    nextState.selectObject(modelObject);
                    return nextState;
                }
                else return this;
            default:
                return this;
        }
    }


}
