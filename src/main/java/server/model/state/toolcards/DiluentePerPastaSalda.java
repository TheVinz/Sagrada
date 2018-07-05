package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.utilities.Choice;
import server.model.state.utilities.Color;
import server.model.state.utilities.GameRules;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static server.model.state.ModelObject.ModelType.*;

/**
 * The <tt>DiluentePerPastaSalda</tt> class represents the "Diluente per pasta salda" {@link ToolCard}. The methods
 * in this class handles the ToolCard's effect: After drafting, return the {@link Dice} to the {@link server.model.state.bag.Bag}
 * and pull one Dice from the Bag. Then choose a value for the drafted Dice and if it is possible place it in your {@link WindowFrame}.
 */
@SuppressWarnings("Duplicates")
public class DiluentePerPastaSalda extends ToolCard {

    private boolean valueSetted;
    private boolean playable;
    private boolean drawDone;
    private DraftPoolCell poolCell;
    private Dice dice;

    /**
     * Initializes the ToolCard DiluentePerPastaSalda.
     * @param model the model of this game.
     */
    public DiluentePerPastaSalda(Model model) {
        super(model);
    }

    /**
     * Gets the number of the ToolCard.
     * @return 11 which represents the DiluentePerPastaSalda ToolCard.
     */
    @Override
    public int getNumber() {
        return 11;
    }

    /**
     * Gets the {@link Color} of the ToolCard.
     * @return the Color RED which represents the ToolCard's color.
     */
    @Override
    public Color getColor() {
        return Color.PURPLE;
    }

    /**
     * Initialize an array with the ToolCard's expected parameters.
     * @param player the {@link Player} whose using the ToolCard.
     * @throws InvalidMoveException if the {@link Player} already moved a Dice and if the {@link server.model.state.boards.draftpool.DraftPool} is empty.
     */
    @Override
    public void start(Player player) throws InvalidMoveException {
        if(player.isDiceMoved())
            throw new InvalidMoveException("You can only place a dice once per turn");
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        expectedParameters=new ArrayDeque<>(3);
        parameters=new ArrayList<>(3);
        expectedParameters.add(WINDOW_FRAME);
        expectedParameters.add(WINDOW_FRAME_CELL);
        this.player=player;
        playable=false;
        drawDone=false;
        valueSetted=false;
    }

    /**
     *
     * @param o
     * @throws InvalidMoveException
     */
    @Override
    public void setParameter(ModelObject o) throws InvalidMoveException {
        if(!drawDone) {
            if(o.getType()!=DRAFT_POOL_CELL) throw new InvalidMoveException("Wrong parameter");
            else {
                parameters.add(o);
                doAbility();
            }
        }
        else if(!valueSetted){
            if(o.getType()==CHOICE) {
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
    void doAbility() throws InvalidMoveException { //SIAMO SICURI CHE VENGA NOTIFICATO LO SPOSTAMENTO AL BAG?
        if(!drawDone) {
            poolCell = (DraftPoolCell) parameters.get(0);
            if(poolCell.isEmpty())
                throw new InvalidMoveException("PoolCell is empty");
            dice = poolCell.removeDice();
            model.getState().getBag().insert(dice);
            dice=model.drawDice(player);
            drawDone=true;
        }
        else if(!valueSetted){
            dice=new Dice(dice.getColor(), ((Choice) parameters.get(1)).getChoice());
            model.putDice(player, dice, poolCell);
            valueSetted=true;
            playable=verify(dice);
            if(!playable)
                model.toolCardUsed(player, this);
        }
        else{
            WindowFrame frame= (WindowFrame) parameters.get(2);
            WindowFrameCell cell= (WindowFrameCell) parameters.get(3);
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
     * Returns true if there is still an expected parameter.
     * @return true if there is still an expected parameter, false if it doesn't.
     */
    @Override
    public boolean hasNext(){
        return !drawDone || !valueSetted || playable;
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
    public Response next() {  //scelta
        if(!drawDone)
            return Response.DRAFT_POOL_CELL;
        else if(!valueSetted)
            return Response.DILUENTE_PER_PASTA_SALDA_CHOICE;
        else if (expectedParameters.peek().equals(WINDOW_FRAME))
            return Response.WINDOW_FRAME;
        else
            return null;
    }

}
