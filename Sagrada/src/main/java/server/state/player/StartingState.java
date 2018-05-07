package server.state.player;

import common.exceptions.InvalidMoveException;
import server.Model;
import server.state.ModelObject;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.windowframe.WindowFrameCell;


public class StartingState implements PlayerState {
    private Model model;
    private Player player;
    private DraftPoolCell draftPoolCell;
    private WindowFrameCell windowFrameCell;
    public StartingState(Model model, Player player){
        this.model = model;
        this.player = player;
        draftPoolCell = null;

    }
    @Override
    public void selectObject(ModelObject  modelObject) throws InvalidMoveException {
        switch (modelObject.getType()){
            case "DraftPoolCell":
                draftPoolCell = (DraftPoolCell) modelObject;
                break;
            case "WindowFrameCell":
                if(draftPoolCell != null) {
                    windowFrameCell = (WindowFrameCell) modelObject;
                    model.move(player, draftPoolCell, windowFrameCell);
                    player.setCurrentState(player.getDiceMovedState());
                }
                else{
                    resetState();
                    throw new InvalidMoveException("Before source cell");
                }
                break;
            case "ToolCard":
                player.getUsingToolCard().selectObject(modelObject);
                player.setCurrentState(player.getUsingToolCard());
                break;
            default:
                resetState();
                throw new InvalidMoveException("Wrong parameter");


        }
    }

    @Override
    public void resetState() {
        draftPoolCell = null;
    }


}
