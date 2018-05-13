package server.viewproxy;

import common.RemoteMVC.RemoteView;
import server.Model;
import server.state.boards.Cell;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.boards.roundtrack.RoundTrackCell;
import server.state.boards.windowframe.WindowFrame;
import server.state.boards.windowframe.WindowFrameCell;
import server.state.boards.windowframe.WindowFrameList;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.player.Player;
import server.state.toolcards.ToolCard;
import server.state.utilities.Color;

import static server.state.ModelObject.*;

public class RMIViewProxy extends ViewProxy{


    private RemoteView remoteView;

    public RMIViewProxy(Model model, int id) {
        super(model, id);
    }

    public void bindRemoteView(RemoteView remoteView){
        this.remoteView=remoteView;
    }

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
    public void updateStartTurn(Player player) {

    }

    @Override
    public void updateDiceDraw(Player player, Color color) {

    }
}
