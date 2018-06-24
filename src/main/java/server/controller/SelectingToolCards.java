package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.player.Player;
import server.model.state.utilities.Choice;

public class SelectingToolCards extends PlayerState {
    public SelectingToolCards(Player player, Model model) {
        super(player, model);
    }

    @Override
    PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException, WrongParameter {
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
