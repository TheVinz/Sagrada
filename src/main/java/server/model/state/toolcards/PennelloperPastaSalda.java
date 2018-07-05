package server.model.state.toolcards;

import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.utilities.Color;
import server.model.state.utilities.GameRules;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.dice.Dice;
import common.exceptions.InvalidMoveException;
import server.model.state.player.Player;

import static server.model.state.ModelObject.ModelType.*;

import java.util.*;

/**
 * The <tt>PennelloPerPastaSalda</tt> class represents the "Pennello per pasta salda" {@link ToolCard}. The methods
 * in this class handles the ToolCard's effect: after drafting, re-roll the drafted {@link Dice} if it cannot be placed
 * return it to the {@link server.model.state.boards.draftpool.DraftPool}.
 */
@SuppressWarnings("Duplicates")
public class PennelloperPastaSalda extends ToolCard {

    private boolean playable;
    private boolean rerollDone;
    private DraftPoolCell poolCell;

    /**
     * Initializes the ToolCard PennelloPerPastaSalda.
     * @param model the model of this game.
     */
    public PennelloperPastaSalda(Model model) {
        super(model);
    }

    /**
     * Gets the number of the ToolCard.
     * @return 6 which represents the PennelloPerPastaSalda ToolCard.
     */
    @Override
    public int getNumber() {
        return 6;
    }

    /**
     * Gets the {@link Color} of the ToolCard.
     * @return the Color PURPLE which represents the ToolCard's color.
     */
    @Override
    public Color getColor() {
        return Color.PURPLE;
    }

    /**
     * Initialize an array with the ToolCard's expected parameters.
     * @param player the {@link Player} whose using the ToolCard.
     * @throws InvalidMoveException if there aren't available moves.
     */
    @Override
    public void start(Player player) throws InvalidMoveException {
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        if(player.isDiceMoved())
            throw new InvalidMoveException("Can only place a dice once per turn");
        expectedParameters=new ArrayDeque<>(3);
        parameters=new ArrayList<>(3);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        this.player=player;
        playable=false;
        rerollDone=false;
    }

    /**
     * @param o
     * @throws InvalidMoveException
     */
    @Override
    public void setParameter(ModelObject o) throws InvalidMoveException {
        if(!rerollDone) {
            if(o.getType()!=DRAFT_POOL_CELL)
                throw new InvalidMoveException("Wrong parameter");
            else {
                parameters.add(o);
                doAbility();
            }
        }
        else {
            if(o.getType()==expectedParameters.peek()){
                expectedParameters.poll();
                parameters.add(o);
                if(expectedParameters.isEmpty()) doAbility();
            }
        }
    }


    @Override
    void doAbility() throws InvalidMoveException {
        if(!rerollDone) {
            poolCell = (DraftPoolCell) parameters.get(0);
            if(poolCell.isEmpty())
                throw new InvalidMoveException("PoolCell is empty");
            Dice dice = poolCell.removeDice();
            dice = new Dice(dice.getColor());
            model.putDice(player, dice, poolCell);
            playable = verify(dice);
            if(!playable)
                model.toolCardUsed(player, this);
            rerollDone=true;
        }
        else{
            WindowFrame frame= (WindowFrame) parameters.get(1);
            WindowFrameCell cell= (WindowFrameCell) parameters.get(2);
            try {
                if (frame != player.getWindowFrame() || !GameRules.validAllCellRestriction(poolCell.getDice(), cell)
                        || !GameRules.validAllDiceRestriction(frame, poolCell.getDice(), cell)) {
                    parameters.remove(frame);
                    parameters.remove(cell);
                    expectedParameters.add(WINDOW_FRAME);
                    expectedParameters.add(WINDOW_FRAME_CELL);
                } else {
                    model.move(player, poolCell, cell);
                    playable=false;
                    player.setDiceMoved();
                    model.toolCardUsed(player, this);
                }
            }
            catch (InvalidMoveException e){
                parameters.remove(frame);
                parameters.remove(cell);
                expectedParameters.add(WINDOW_FRAME);
                expectedParameters.add(WINDOW_FRAME_CELL);
            }
        }
    }

    /**
     * @return
     */
    @Override
    public boolean hasNext(){
        return !rerollDone || playable;
    }

    private boolean verify(Dice dice) {
        for(int i=0; i<WindowFrame.ROWS; i++) {
            for(int j=0; j<WindowFrame.COLUMNS; j++){
                if (GameRules.validAllDiceRestriction(player.getWindowFrame(), dice, player.getWindowFrame().getCell(i,j))
                        && GameRules.validAllCellRestriction(dice, player.getWindowFrame().getCell(i,j)))
                    return true;
            }
        }
        return false;
    }

    /**
     * Informs the player which the next parameters are.
     * @return a Response which represents the next parameter.
     */
    @Override
    public Response next(){
        if(!rerollDone)
            return Response.DRAFT_POOL_CELL;
        else if (expectedParameters.peek().equals(WINDOW_FRAME))
            return Response.WINDOW_FRAME;
        else
            return null;
    }

}