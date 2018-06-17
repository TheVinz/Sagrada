package server.controller;

import common.response.Response;
import server.model.state.ModelObject.ModelObject;
import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.ModelObject.ModelType;
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
    public PlayerState selectObject(ModelObject modelObject) {
        if(modelObject.getType()==ModelType.CHOICE) {
            model.windowFrameChoice(player, windowFrameLists[((Choice) modelObject).getChoice()]);
            if(model.isSingleplayer())
                return new SelectingToolCards(player, model);
            else
                return new WaitingState(player, model);
        }
        else return this;
    }

    @Override
    public void abort(){
        selectObject(new Choice(0));
    }

}
