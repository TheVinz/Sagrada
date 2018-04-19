package TestGUI.client.view;

import TestGUI.client.toolcards.PennelloPerEglomise;
import TestGUI.client.toolcards.ToolCard;
import TestGUI.common.remotemvc.RemoteView;
import TestGUI.common.viewchangement.*;

public class ModelProxy implements RemoteView {
    PlayerViewController view;

    public ModelProxy(PlayerViewController viewController) {
        this.view=viewController;
    }

    @Override
    public void sendChangement(Changement c) {
        switch (c.getType()) {
            case ChangementTypes.MOVE:
                handleMoveChangement((Move) c);
                break;
            case ChangementTypes.REFILLED_DRAFT_POOL:
                handleRefilledDraftPool((RefilledDraftPool) c);
                break;
            case ChangementTypes.CELL_UPDATE:
                handleCellUpdate((CellUpdate) c);
                break;
            case ChangementTypes.LOAD_TOOL_CARDS:
                handleLoadToolCards((LoadToolCards) c);
            default:
                break;
        }
    }

    private void handleLoadToolCards(LoadToolCards c) {
        ToolCard[] toolCards=new ToolCard[1];
        for(int i=0; i<toolCards.length; i++){
            switch(c.getToolCards()[i]){
                case "PennelloPerEglomise":
                    toolCards[i]=new PennelloPerEglomise();
                    break;
            }
        }
        view.setToolCards(toolCards);
    }

    private void handleCellUpdate(CellUpdate c) {
        switch(c.getCellType()){
            case CellUpdate.WINDOW_FRAME_CELL:
                view.updateWindowFrameCell(c.getRow(), c.getColumn(), c.getImage());
                break;
            case CellUpdate.DRAFT_POOL_CELL:
                view.updateDraftPoolCell(c.getRow(), c.getImage());
                break;
            default:
                break;
        }
    }

    private void handleRefilledDraftPool(RefilledDraftPool c) {
        String[] dices=c.getDices();
        view.refillDraftPool(dices);
    }

    private void handleMoveChangement(Move c) {
        switch(c.getMoveType()){
            case Move.FROM_DP_TO_WF:
                view.moveFromDPtoWF(c.getSourceX(), c.getTargetX(), c.getTargetY());
                break;
            case Move.FROM_WF_TO_WF:
                view.moveFromWFtoWF(c.getSourceX(), c.getSourceY(), c.getTargetX(), c.getTargetY());
                break;
            default:
                break;
        }
    }

}
