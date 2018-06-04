package server.controller;

import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
import server.model.Model;
import server.model.state.ModelObject.ModelObject;
import server.model.state.ModelObject.ModelType;
import server.model.state.player.Player;
import server.model.state.utilities.Choice;

public class SelectingPrivateObjectiveCard extends PlayerState{
    public SelectingPrivateObjectiveCard(Player player, Model model) {
        super(player, model);
    }

    @Override
    PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException, WrongParameter {
        System.out.println("DEBUUUUUUUUUUUUG");
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
