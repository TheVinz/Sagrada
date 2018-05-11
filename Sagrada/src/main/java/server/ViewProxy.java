package server;

import common.command.Command;
import common.exceptions.InvalidMoveException;
import common.remotemvc.RemoteModel;
import common.remotemvc.RemoteView;
import common.viewchangement.Changement;
import common.viewchangement.StartTurn;
import server.controller.Controller;
import server.observer.Observer;
import server.state.State;
import server.state.boards.Cell;
import server.state.boards.windowframe.WindowFrameList;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.player.Player;
import server.state.toolcards.ToolCard;
import server.state.utilities.Color;

import static common.command.Command.*;

public class ViewProxy implements RemoteView, Observer {
    private Controller controller;
    private State state;
    private RemoteModel remoteModel;
    private int id;

    public ViewProxy(Model model, int id){
        model.addObserver(this);
        state=model.getState();
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
                controller.selectObject(state.getDraftPool().getCell(c.getX()));
                break;
            case WINDOW_FRAME_CLICK:
                controller.selectObject(state.getWindowFrame(id));
                controller.selectObject(state.getWindowFrame(id).getCell(c.getX(), c.getY()));
                break;
            case ROUND_TRACK_CLICK:
                controller.selectObject(state.getRoundTrack().getRoundSet(c.getX()).get(c.getY()));
                break;
            case USE_TOOL_CARD:
                controller.selectObject(state.getToolCard(c.getX()));
                break;
            /*case END_TURN:*/
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
    public void updateWindowFrameChoices(WindowFrameList[] windowFrameLists) {

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

    }

    @Override
    public void updateDiceDrow(Player player, Color color) {

    }

}
