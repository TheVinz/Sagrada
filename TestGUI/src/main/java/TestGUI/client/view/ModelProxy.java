package TestGUI.client.view;

import TestGUI.common.remotemvc.RemoteView;
import TestGUI.common.viewchangement.Changement;
import TestGUI.common.viewchangement.ChangementTypes;
import TestGUI.common.viewchangement.Move;
import TestGUI.common.viewchangement.RefilledDraftPool;

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
        }
    }

}
