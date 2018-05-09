package server.controller;

import common.exceptions.InvalidMoveException;
import server.state.ModelObject;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.player.Player;
import server.Model;

public class MovingDice implements PlayerState {

    private Player player;
    private Model model;

    private DraftPoolCell source;
    private WindowFrameCell target;

    public MovingDice(Player player, Model model){
        this.player=player;
        this.model=model;
        source=null;
        target=null;
    }

    @Override
    public PlayerState selectObject(ModelObject modelObject) throws InvalidMoveException {
        switch(modelObject.getType()){
            case WindowFrameCell:
                target=(WindowFrameCell) modelObject;
                model.move(player, source, target);

        }
    }
}
