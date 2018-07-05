package server.model.state.toolcards;

import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.Cell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.utilities.Color;

/**
 *  The <tt>Martelletto</tt> class represents the "Martelletto" {@link ToolCard}. The methods
 *  in this class handles the ToolCard's effect: Re-roll all {@link Dice} in the {@link server.model.state.boards.draftpool.DraftPool}.
 *  This may only be used on your second turn before drafting.
 */
public class Martelletto extends ToolCard {

    /**
     * Initializes the ToolCard Martelletto.
     * @param model the model of this game.
     */
    public Martelletto(Model model) { super(model); }

    /**
     * Gets the number of the ToolCard.
     * @return 7 which represents the Martelletto ToolCard.
     */
    @Override
    public int getNumber() {
        return 7;
    }

    /**
     * Gets the {@link Color} of the ToolCard.
     * @return the Color BLUE which represents the ToolCard's color.
     */
    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    /**
     * Initialize an array with the ToolCard's expected parameters.
     * @param player the {@link Player} whose using the ToolCard.
     * @throws InvalidMoveException if there aren't Dice in the DraftPool, if it isn't the second move of the
     *                              {@link Player} and if the player already place a Dice.
     */
    @Override
    public void start(Player player) throws InvalidMoveException {
        if(model.getState().getDraftPool().isEmpty())
            throw new InvalidMoveException("Draft pool is empty");
        if(!player.isSecondTurn())
            throw new InvalidMoveException("Can be used only on second move");
        if(player.isDiceMoved())
            throw new InvalidMoveException("Can be used only before placing a dice");
        this.player=player;
        doAbility();
    }

    /**
     * @return
     */
    @Override
    public boolean hasNext(){
        return false;
    }

    @Override
    void doAbility() throws InvalidMoveException {
        for(Cell cell : model.getState().getDraftPool().getDraftPool()){
            if(!cell.isEmpty()){
                Dice dice=cell.removeDice();
                model.putDice(player, new Dice(dice.getColor()), cell);
            }
        }
        model.toolCardUsed(player, this);
    }
}
