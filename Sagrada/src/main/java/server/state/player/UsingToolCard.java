package server.state.player;

import common.exceptions.InvalidMoveException;
import server.state.ModelObject;
import server.state.toolcards.ToolCard;

public class UsingToolCard implements PlayerState {


    ToolCard toolCard;
    @Override
    public void selectObject(ModelObject modelObject) throws InvalidMoveException {

    }

    @Override
    public void resetState() {

    }


}
