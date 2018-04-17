package TestGUI.server;

import TestGUI.common.Command;
import TestGUI.server.model.Model;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.boards.draftpool.DraftPoolCell;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;
import TestGUI.server.model.exceptions.InvalidMoveException;
import javafx.scene.paint.Color;

public class Controller {
    private Model model;
    private boolean firstMove=true;

    private DraftPoolCell picked;

    public Controller(Model model){
        this.model=model;
    }

    public void simpleMove(DraftPoolCell source, WindowFrameCell target) throws InvalidMoveException {
        if(firstMove){
            if(!GameRules.validFirstMove(model.getWindowFrame(), target)) throw new InvalidMoveException("First move near borsers, please.");
            firstMove=false;
        }
        else{
            if(!GameRules.validAdjacentDices(model.getWindowFrame(), target))
                throw new InvalidMoveException("Dice must have an another adjacent one.");
            if(!GameRules.validAdjacentDiceColors(model.getWindowFrame(), source.getDice().getColor(), target))
                throw new InvalidMoveException("Adjacent colors must be different.");
            else if(!GameRules.validAdjacentShapes(model.getWindowFrame(), source.getDice().getValue(), target))
                throw new InvalidMoveException("Adjacent shapes must be different.");
        }
        model.simpleMove(source, target);
    }


    public void refillDraftPool() throws InvalidMoveException {
        if(!model.getDraftPool().isEmpty()) throw new InvalidMoveException("Not Empty Draft Pool");
        else model.refillDraftPool();
    }

    public void sendCommand(Command command) throws InvalidMoveException {
        switch (command.getType()){
            case Command.DRAFTPOOL_CLICK:
                picked=model.getDraftPool().get(command.getX());
                break;
            case Command.WINDOW_FRAME_CLICK:
                if(picked!=null){
                    WindowFrameCell target= model.getWindowFrame().getCell(command.getX(), command.getY());
                    simpleMove(picked, target);
                }
                break;
            case Command.REFILL_DRAFTPOOL:
                refillDraftPool();
        }
    }
}
