package server.viewproxy;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.exceptions.InvalidMoveException;
import server.model.Model;
import server.model.state.boards.Cell;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.roundtrack.RoundTrackCell;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;

import static common.command.GameCommand.*;
import static server.model.state.ModelObject.*;

public class RMIViewProxy extends ViewProxy implements RemoteController {


    private RemoteView remoteView;

    public RMIViewProxy(Model model, Player player) {
        super(model, player);
    }

    public void bindRemoteView(RemoteView remoteView){
        this.remoteView=remoteView;
    }


    //da ViewProxy
    @Override
    public void updateMove(Player player, Cell source, Cell target) {
        switch(source.getType()){
            case WINDOW_FRAME_CELL:
                if (target.getType() == WINDOW_FRAME_CELL)
                    remoteView.move(player.getId(), WINDOW_FRAME_CELL, WINDOW_FRAME_CELL, ((WindowFrameCell) source).getRow(), ((WindowFrameCell) source).getColumnn(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn());
                break;
            case DRAFT_POOL_CELL:
                if(target.getType()==WINDOW_FRAME_CELL)
                    remoteView.move(player.getId(), DRAFT_POOL_CELL, WINDOW_FRAME_CELL, ((DraftPoolCell) source).getIndex(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn());
                break;
            case ROUND_TRACK_CELL:
                if(target.getType() == WINDOW_FRAME_CELL)
                    remoteView.move(player.getId(), ROUND_TRACK_CELL, WINDOW_FRAME_CELL, ((RoundTrackCell) source).getRound(), ((RoundTrackCell) source).getIndex(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn());
                    break;
            default:
                break;
        }
    }

    @Override
    public void updateCellChangement(Player player, Cell cell) {
        switch(cell.getType()){
            case WINDOW_FRAME_CELL:
                remoteView.updateCell(player.getId(), WINDOW_FRAME_CELL, ((WindowFrameCell) cell).getRow(), ((WindowFrameCell) cell).getColumnn(), cell.getDice().getValue(), cell.getDice().getColor().asChar());
                break;
            case DRAFT_POOL_CELL:
                remoteView.updateCell(player.getId(), DRAFT_POOL_CELL, ((DraftPoolCell) cell).getIndex(), cell.getDice().getValue(), cell.getDice().getColor().asChar());
                break;
            case ROUND_TRACK_CELL:
                remoteView.updateCell(player.getId(), ROUND_TRACK_CELL, ((RoundTrackCell) cell).getRound(), ((RoundTrackCell) cell).getIndex(), cell.getDice().getValue(), cell.getDice().getColor().asChar());
                break;
            default:
                break;
        }
    }

    @Override
    public void updateRefillDraftPool(Cell[] draftPool) {
        char[] colors = new char[draftPool.length];
        int[] values = new int[draftPool.length];
        for(int i=0; i<draftPool.length; i++){
            colors[i]=draftPool[i].getDice().getColor().asChar();
            values[i]=draftPool[i].getDice().getValue();
        }
        remoteView.refilledDraftPool(values, colors);
    }

    @Override
    public void updateToolCards(ToolCard[] toolCards) {
        int[] cards=new int[toolCards.length];
        for(int i=0; i<toolCards.length; i++)
            cards[i]=toolCards[i].getNumber();
        remoteView.loadToolCards(cards);
    }

    @Override
    public void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards) {
        int[] cards= new int[publicObjectiveCards.length];
        for(int i=0; i<cards.length; i++)
            cards[i]=publicObjectiveCards[i].getNumber();
        remoteView.loadPublicObjectiveCards(cards);
    }

    @Override
    public void updateWindowFrameChoices(WindowFrameList[] windowFrameLists) {
        int[] favorTokens= new int[windowFrameLists.length];
        String[] reps=new String[favorTokens.length];
        for(int i=0; i<windowFrameLists.length; i++){
            favorTokens[i]=windowFrameLists[i].getFavorToken();
            reps[i]=windowFrameLists[i].getRep();
        }
        remoteView.loadWindowFrameChoice(reps, favorTokens);
    }

    @Override
    public void updatePlayers(Player[] players) {
        String[] names=new String[players.length];
        int[] ids = new int[players.length];
        String[] windowFrameReps = new String[players.length];
        int[] windowFrameFavorTokens = new int [players.length];
        for(int i=0; i<players.length; i++){
            names[i]=players[i].getName();
            ids[i]=players[i].getId();
            windowFrameReps[i]=players[i].getWindowFrame().getRep();
            windowFrameFavorTokens[i]=players[i].getWindowFrame().getFavorToken();
        }
        remoteView.loadPlayers(names, ids, windowFrameReps, windowFrameFavorTokens);
    }

    @Override
    public void updateToolCardUsed(Player player, ToolCard toolCard) {
        int index=-1;
        for(int i=0; i<state.getToolCards().length; i++){
            if(state.getToolCards()[i].equals(toolCard))
                index=i;
        }
        if(index>-1)
            remoteView.toolCardUsed(player.getId(), index);
    }

    @Override
    public void updatePrivateObjectiveCard(PrivateObjectiveCard card) {
        remoteView.loadPrivateObjectiveCard(card.getColor());
    }

    @Override
    public void updateStartTurn(Player player) {
        remoteView.newTurn(player.getId());
    }

    @Override
    public void updateDiceDraw(Player player, Color color) {
        remoteView.notifyDiceDraw(player.getId(), color.asChar());
    }


    //da RemoteController
    @Override
    public void command(int type) {
        switch(type){
            case END_TURN:
                controller.endTurn();
                break;
            default:
                break;
        }
    }
    @Override
    public void command(int type, int index) throws InvalidMoveException {
        switch(type){
            case DRAFTPOOL_CLICK:
                controller.selectObject(state.getDraftPool().getCell(index));
                break;
            case USE_TOOL_CARD:
                controller.selectObject(state.getToolCard(index));
                break;
            default:
                break;
        }

    }
    @Override
    public void command(int type, int param1, int param2) throws InvalidMoveException {
        switch(type){
            case WINDOW_FRAME_CLICK:
                controller.selectObject(player.getWindowFrame().getCell(param1, param2));
                break;
            case ROUND_TRACK_CLICK:
                controller.selectObject(state.getRoundTrack().getRoundSet(param1).get(param2));
                break;
            default:
                break;
        }
    }
}
