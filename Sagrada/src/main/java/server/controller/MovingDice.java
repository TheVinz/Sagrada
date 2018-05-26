package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.utilities.GameRules;

public class MovingDice extends PlayerState {

    private DraftPoolCell source = null;
    private WindowFrameCell target = null;
    private Response nextParameter = null;

    public MovingDice(Player player, Model model) {
        super(player, model);
    }


    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException {
        switch(modelObject.getType()){
            case WINDOW_FRAME:
                if(player.getWindowFrame()!=((WindowFrame) modelObject))
                    throw new InvalidMoveException("Invalid frame");
                else return this;

            case WINDOW_FRAME_CELL:
                target=(WindowFrameCell) modelObject;
                if(!player.isFirstMoveDone()) {
                    if (!GameRules.validFirstMove(player.getWindowFrame(), target))
                        throw new InvalidMoveException("Select a cell near borders");
                    else if (!GameRules.validAllCellRestriction(source.getDice(), target))
                        throw new InvalidMoveException("Cell restriction must be respected");
                }
                else if(!GameRules.validAllDiceRestriction(player.getWindowFrame(), source.getDice(), target))
                    throw new InvalidMoveException("All adjacent dice restrictions must be respected");
                else if(!GameRules.validAllCellRestriction(source.getDice(), target))
                    throw new InvalidMoveException("Cell restriction must be respected");

                model.move(player, source, target);
                player.setDiceMoved();
                nextParameter = Response.SUCCESS;
                return new WaitingState(player, model);

            case DRAFT_POOL_CELL:
                source=(DraftPoolCell) modelObject;
                if(source.isEmpty())
                    throw new InvalidMoveException("Source cell is empty, try again");
                return this;

            default:
                return this;
        }
    }

    @Override
    public Response nextParam(){
        return nextParameter;
    }

}
