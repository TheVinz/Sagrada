package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import common.response.Response;
import server.model.state.ModelObject.ModelObject;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.player.Player;
import server.model.Model;
import server.model.state.utilities.GameRules;

/**
 *The <tt>MovingDice</tt> class represents the {@link PlayerState} for moving a dice from the draft pool to the window frame.
 * Eventually during this phase the player can decide to use a {@link server.model.state.toolcards.ToolCard}'s effect.
 */
public class MovingDice extends PlayerState {

    private DraftPoolCell source = null;
    private WindowFrameCell target = null;
    private Response nextParameter = null;

    /**
     * Initializes a new <tt>MovingDice</tt> phase setting this game's {@link Model} and the {@link Player} that is performing
     * this move.
     * @param player the player associated with this state.
     * @param model the model of the game.
     */
    MovingDice(Player player, Model model) {
        super(player, model);
    }


    /**
     * Selects the source dice for a simple {@link server.model.state.boards.draftpool.DraftPool} to {@link WindowFrame}
     * move if the {@link ModelObject} is a cell, {@link DraftPoolCell} and {@link WindowFrameCell} respectively, or start
     * using a {@link server.model.state.toolcards.ToolCard} in case of tool card type. Returns the appropriate new GamePhase.
     * @param modelObject the client's input.
     * @return a new {@link WaitingState} in case of successful move, <code>this</code> in case of WindowFrameCell object or
     * a new {@link UsingToolCard} in case of ToolCard object.
     * @throws InvalidMoveException if the client is trying to perform a not valid move, aborting this move.
     * @throws WrongParameter if the client is trying to perform a not valid move, without aborting this move.
     */
    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException, WrongParameter {
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
                nextParameter = Response.SUCCESS_MOVE_DONE;
                player.getTimer().start();
                return new WaitingState(player, model);

            case TOOL_CARD:
                PlayerState next = new UsingToolCard(player, model);
                return next.selectObject(modelObject);

            case DRAFT_POOL_CELL:
                source=(DraftPoolCell) modelObject;
                if(source.isEmpty())
                    throw new InvalidMoveException("Source cell is empty, try again");
                return this;

            default:
                return this;
        }
    }

    /**
     * Returns the next move the player is required to perform.
     * @return the response representing the move the player is required to perform.
     */
    @Override
    public Response nextParam(){
        return nextParameter;
    }

}
