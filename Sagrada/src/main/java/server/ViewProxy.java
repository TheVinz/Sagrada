package server;

import common.command.Command;
import common.exceptions.InvalidMoveException;
import common.remotemvc.RemoteModel;
import common.remotemvc.RemoteView;
import common.viewchangement.Changement;
import common.viewchangement.StartTurn;
import server.observer.Observer;
import server.state.State;
import server.state.boards.Cell;
import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.player.Player;
import server.state.toolcards.ToolCard;

import static common.command.Command.*;

public class ViewProxy implements RemoteView, Observer {
    private Controller controller;
    private State state;
    private RemoteModel remoteModel;
    private int id;

    public ViewProxy(Model model, int id){
        model.addObserver(this);
        controller=new Controller(model, state.getPlayer(id));
        this.id=id;
    }

    @Override
    public void bindRemoteModel(RemoteModel model){
        this.remoteModel=model;
    }

    @Override
    public void sendChangement(Changement change) {

    }

    @Override
    public void receiveCommand(Command c) throws InvalidMoveException {
        switch(c.getType()){
            case DRAFTPOOL_CLICK:
                controller.draftPoolClick(state.getDraftPool().getCell(c.getX()));
                break;
            case WINDOW_FRAME_CLICK:
                controller.windowFrameClick(state.getWindowFrame(id), state.getWindowFrame(id).getCell(c.getX(), c.getY()));
                break;
            case ROUND_TRACK_CLICK:
                controller.roundTrackClick(c.getX(), c.getY());
                break;
            case USE_TOOL_CARD:
                controller.useToolCard(c.getX());
                break;
            default:
                break;
        }
    }

    @Override
    public void updateMove(Cell source, Cell target) {

    }

    @Override
    public void updateCellChangement(Cell cell) {

    }

    @Override
    public void updateRefillDraftPool(Cell[] draftPool) {

    }

    @Override
    public void updateToolCards(ToolCard[] toolCards) {

    }

    @Override
    public void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards) {

    }

    @Override
    public void updateWindowFrameChoices(WindowFrame[] windowFrames) {

    }

    @Override
    public void updatePlayers(Player[] players) {

    }

    @Override
    public void updateToolCardUsed(Player player, ToolCard toolCard) {

    }

    @Override
    public void updatePrivateObjectiveCard(PrivateObjectiveCard card) {

    }

    @Override
    public void updateStartTurn() {
        controller.startTurn();
        remoteModel.receiveChangement(new StartTurn());
    }
}
