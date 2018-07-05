package server.controller;

import common.exceptions.WrongParameter;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.player.Player;
import server.model.state.utilities.Choice;

/**
 * The <tt>SelectingToolCards</tt> class is the {@link PlayerState} representing the beginning of a single-player game,
 * where the player is required to choose the difficulty of his game.
 */
public class SelectingToolCards extends PlayerState {

    /**
     * Initializes a new <tt>SelectingToolCards</tt> setting the {@link Model} of the current game and the {@link Player} related
     * to the actions represented by this state.
     * @param player the player associated to this state.
     * @param model the model of the current game.
     */
    public SelectingToolCards(Player player, Model model) {
        super(player, model);
    }

    /**
     * If the {@link ModelObject} is a {@link Choice} sets the game difficulty tho the Choice's value (if this value is
     * less than 1 or more than 5 a {@link WrongParameter is thrown}).
     * @param modelObject the object representing the client input.
     * @return <code>this</code> if the modelObject is not a {@link Choice}, a new {@link WaitingState} otherwise.
     * @throws WrongParameter if the Choice's value is less than 1 or more than 5.
     */
    @Override
    PlayerState selectObject(ModelObject modelObject) throws WrongParameter {
        if(modelObject.getType()==ModelType.CHOICE) {
            int choice = ((Choice) modelObject).getChoice();
            if(choice < 1 || choice > 5 )
                throw new WrongParameter("Invalid input");
            else
                model.toolCardsChoice(choice);
            return new WaitingState(player, model);
        }
        else return this;
    }
}
