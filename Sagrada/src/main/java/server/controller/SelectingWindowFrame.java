package server.controller;

import common.ModelObject;
import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.player.Player;
import server.model.state.utilities.Choice;

public class SelectingWindowFrame extends PlayerState {

    WindowFrameList[] windowFrameLists;

    public SelectingWindowFrame(Player player, Model model, WindowFrameList[] windowFrameLists) {
        super(player, model);
        this.windowFrameLists=windowFrameLists;
    }

    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException {
        if(modelObject.getType()==ModelObject.CHOICE) {
            try {
                model.windowFrameChoice(player, windowFrameLists[((Choice) modelObject).getChoice()]);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidMoveException("Index out of bound");
            }
            return new WaitingState(player, model);
        }
        else return this;
    }

    @Override
    public int nextParam() {
        return ModelObject.CHOICE;
    }
}
