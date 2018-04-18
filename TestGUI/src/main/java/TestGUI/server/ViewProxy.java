package TestGUI.server;

import TestGUI.client.view.ModelProxy;
import TestGUI.common.Command;
import TestGUI.common.Observer;
import TestGUI.common.remotemvc.RemoteController;
import TestGUI.common.remotemvc.RemoteView;
import TestGUI.common.viewchangement.Changement;
import TestGUI.common.viewchangement.Move;
import TestGUI.common.viewchangement.RefilledDraftPool;
import TestGUI.server.model.Model;
import TestGUI.server.model.boards.draftpool.DraftPoolCell;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;
import TestGUI.server.model.exceptions.InvalidMoveException;

public class ViewProxy implements Observer, RemoteController {
    private RemoteView clientView;
    private Controller controller;
    private Model model;

    DraftPoolCell picked;

    public ViewProxy(Model model, Controller controller){
        this.model=model;
        this.controller=controller;
    }

    public void bindClientView(ModelProxy view) {
        this.clientView = view;
    }

    @Override
    public void sendCommand(Command command) throws InvalidMoveException {
        switch (command.getType()) {
            case Command.DRAFTPOOL_CLICK:
                picked = model.getDraftPool().get(command.getX());
                break;
            case Command.WINDOW_FRAME_CLICK:
                if (picked != null) {
                    WindowFrameCell target = model.getWindowFrame().getCell(command.getX(), command.getY());
                    controller.simpleMove(picked, target);
                }
                break;
            case Command.REFILL_DRAFTPOOL:
                controller.refillDraftPool();
        }
    }

    @Override
    public void updateSimpleMove(DraftPoolCell source, WindowFrameCell target) {
        clientView.sendChangement(new Move(source.getIndex(), target.getX(), target.getY()));
    }

    @Override
    public void updateRefillDraftPool() {
        clientView.sendChangement(new RefilledDraftPool(model.getDraftPool().asImage()));
    }
}
