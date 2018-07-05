package server.controller;

import common.exceptions.WrongParameter;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.player.Player;
import server.model.state.utilities.Choice;

/**
 * The <tt>SelectingPrivateObjectiveCard</tt> class is the {@link PlayerState} representing the {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard}
 * choice the client, for single-player games, is required to perform in order to calculate his points and end the game.
 */
public class SelectingPrivateObjectiveCard extends PlayerState{

    /**
     * Initializes a new <tt>SelectingPrivateObjectiveCard</tt> setting the {@link Model} of the current game and the {@link Player} related
     * to the action represented by this state.
     * @param player the player associated to this state.
     * @param model the model of the current game.
     */
    public SelectingPrivateObjectiveCard(Player player, Model model) {
        super(player, model);
    }

    /**
     * If the {@link ModelObject} is a {@link Choice} whose value is 0 or 1 (if it is not a {@link WrongParameter will be thrown})
     * selects the corresponding PrivateObjectiveCard, otherwise ignores the input and returns <code>this</code>.
     * @param modelObject the object representing the client input.
     * @return <code>this</code> if the ModelObject is not a Choice, a new {@link WaitingState} otherwise.
     * @throws WrongParameter if the Choice's value is different from 0 and 1.
     */
    @Override
    PlayerState selectObject(ModelObject modelObject) throws WrongParameter {
        if(modelObject.getType()==ModelType.CHOICE) {
            int choice = ((Choice) modelObject).getChoice();
            if(choice != 0 && choice != 1 )
                throw new WrongParameter("Invalid input");
            else
                model.privateCardChoice(choice);
            return new WaitingState(player, model);
        }
        else return this;
    }
}
