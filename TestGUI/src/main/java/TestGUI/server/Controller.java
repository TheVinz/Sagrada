package TestGUI.server;

import TestGUI.server.model.Model;
import TestGUI.server.model.boards.draftpool.DraftPoolCell;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;
import TestGUI.server.model.exceptions.InvalidMoveException;
import TestGUI.server.model.toolcards.ToolCard;
import javafx.beans.property.SimpleObjectProperty;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class Controller {
    private Model model;

    private boolean firstMove=true;
    private DraftPoolCell picked=null;
    private ToolCard[] toolCards;
    private ToolCard activeToolCard;


    public Controller(Model model){
        this.model=model;
        this.toolCards=model.getToolCards();
    }

    public void draftPoolCkick(DraftPoolCell cell) throws InvalidMoveException {
        if(activeToolCard!=null){
            if(activeToolCard.nextParameter()!=DraftPoolCell.class) {
                activeToolCard=null;
                throw new InvalidMoveException("Invalid parameter");
            }
            else {
                boolean end;
                try{
                    end=activeToolCard.setParameter(cell);
                }
                catch(InvalidMoveException e){
                    activeToolCard=null;
                    throw e;
                }
                if(end) activeToolCard=null;
            }
        }
        else{
            picked=cell;
        }
    }

    public void windowFrameClick(WindowFrameCell cell) throws InvalidMoveException {
        if(activeToolCard!=null){
            if(activeToolCard.nextParameter()!=WindowFrameCell.class) {
                activeToolCard=null;
                throw new InvalidMoveException("Invalid parameter");
            }
            else {
                boolean end;
                try {
                    end = activeToolCard.setParameter(cell);
                } catch (InvalidMoveException e) {
                    activeToolCard = null;
                    throw e;
                }
                if(end) activeToolCard=null;
            }
        }
        else if (picked != null){
            simpleMove(picked, cell);
        }
    }

    public void useToolCard(int index){
        activeToolCard=toolCards[index];
        activeToolCard.start();
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
        model.move(source, target);
    }


    public void refillDraftPool() throws InvalidMoveException {
        if(!model.getDraftPool().isEmpty()) throw new InvalidMoveException("Not Empty Draft Pool");
        else model.refillDraftPool();
    }

}
