package TestGUI.server;

import TestGUI.client.view.ModelProxy;
import TestGUI.common.Command;
import TestGUI.common.Observer;
import TestGUI.common.remotemvc.RemoteController;
import TestGUI.common.remotemvc.RemoteView;
import TestGUI.common.viewchangement.*;
import TestGUI.server.model.Model;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.boards.draftpool.DraftPoolCell;
import TestGUI.server.model.boards.windowframe.WindowFrame;
import TestGUI.server.model.boards.windowframe.WindowFrameCell;
import TestGUI.server.model.exceptions.InvalidMoveException;
import TestGUI.server.model.toolcards.ToolCard;

public class ViewProxy implements Observer, RemoteController {
    private RemoteView clientView;
    private Controller controller;
    private Model model;

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
                controller.draftPoolCkick(model.getDraftPool().get(command.getX()));
                break;
            case Command.WINDOW_FRAME_CLICK:
                controller.windowFrameClick(model.getWindowFrame().getCell(command.getX(),command.getY()));
                break;
            case Command.REFILL_DRAFTPOOL:
                controller.refillDraftPool();
                break;
            case Command.USE_TOOL_CARD:
                controller.useToolCard(command.getX());
                break;

        }
    }

    @Override
    public void updateMove(Cell source, Cell target) {
        if(source.getClass()==DraftPoolCell.class){
            DraftPoolCell cell1=(DraftPoolCell) source;
            if(target.getClass()==WindowFrameCell.class) {
                WindowFrameCell cell2=(WindowFrameCell) target;
                clientView.sendChangement(new Move(cell1.getIndex(), cell2.getX(), cell2.getY()));
            }
        }
        else if(source.getClass()==WindowFrameCell.class){
            WindowFrameCell cell1 = (WindowFrameCell) source;
            if(target.getClass()==WindowFrameCell.class){
                WindowFrameCell cell2 = (WindowFrameCell) target;
                clientView.sendChangement(new Move(cell1.getX(), cell1.getY(), cell2.getX(), cell2.getY()));
            }
        }
    }

    @Override
    public void updateRefillDraftPool() {
        clientView.sendChangement(new RefilledDraftPool(model.getDraftPool().asImage()));
    }

    @Override
    public void updateCell(Cell cell) {
        if(cell.getClass()==WindowFrameCell.class){
            WindowFrameCell cell1=(WindowFrameCell) cell;
            clientView.sendChangement(new CellUpdate(cell1.getX(), cell1.getY(), cell.asImage()));
        }
        else if(cell.getClass()==DraftPoolCell.class){
            DraftPoolCell cell1 = (DraftPoolCell) cell;
            clientView.sendChangement(new CellUpdate(cell1.getIndex(), cell.asImage()));
        }
    }

    @Override
    public void loadToolCards(ToolCard[] toolCards) {
        String[] names=new String[1];
        for(int i=0; i<names.length; i++){
            names[i]=toolCards[i].getName();
        }
        clientView.sendChangement(new LoadToolCards(names));
    }

}
