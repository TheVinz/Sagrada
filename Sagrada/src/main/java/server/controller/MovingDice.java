package server.controller;

import common.exceptions.InvalidMoveException;
import server.state.ModelObject;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.player.Player;
import server.Model;
import server.state.utilities.GameRules;

public class MovingDice implements PlayerState {

    private Player player;
    private Model model;

    private DraftPoolCell source;
    private WindowFrameCell target;

    public MovingDice(Player player, Model model){
        this.player=player;
        this.model=model;
        source=null;
        target=null;
    }

    private boolean firstMove() {
        return model.getState().getRoundTrack().getRound()==1 && !player.isFirstMoveDone();
    }

    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException {
        switch(modelObject.getType()){
            case ModelObject.WINDOW_FRAME:
                if(player.getWindowFrame()!=((WindowFrame) modelObject))
                    throw new InvalidMoveException("Invalid frame");
                else return this;

            case ModelObject.WINDOW_FRAME_CELL:
                target=(WindowFrameCell) modelObject;
                if(firstMove())
                    if(GameRules.validFirstMove(player.getWindowFrame(), target)){
                        model.move(player, source, target);
                        return new WaitingState(player, model);
                    }
                    else throw new InvalidMoveException("Select a cell near borders");
                else if(GameRules.validAllDiceRestriction(player.getWindowFrame(), source.getDice(), target) &&
                    GameRules.validAllCellRestriction(source.getDice(), target)) {
                    model.move(player, source, target);
                    return new WaitingState(player, model);
                }
                else throw new InvalidMoveException("Invalid move");

            case ModelObject.DRAFT_POOL_CELL:
                source=(DraftPoolCell) modelObject;
                return this;

            default:
                return this;
        }
    }
}
