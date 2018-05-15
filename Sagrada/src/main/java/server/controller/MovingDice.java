package server.controller;

import common.exceptions.InvalidMoveException;
import common.ModelObject;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.utilities.GameRules;

public class MovingDice extends PlayerState {

    private DraftPoolCell source = null;
    private WindowFrameCell target = null;

    public MovingDice(Player player, Model model) {
        super(player, model);
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

    @Override
    public int nextParam() {
        return ModelObject.WINDOW_FRAME_CELL;
    }
}
