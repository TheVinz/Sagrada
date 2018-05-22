package server.viewproxy;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.response.Response;
import server.controller.Controller;
import server.model.Model;
import server.model.state.ModelObject.ModelType;
import server.model.state.State;
import server.model.state.boards.Cell;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.roundtrack.RoundTrackCell;
import server.model.state.boards.windowframe.WindowFrameCell;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Choice;
import server.model.state.utilities.Color;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIViewProxy extends UnicastRemoteObject implements ViewProxy,RemoteController {

    private Player player;
    private State state;
    private Controller controller;
    private RemoteView remoteView;

    public RMIViewProxy(Model model, Player player) throws RemoteException{
        super();
        this.controller=new Controller(model, player, this);
        this.player=player;
        this.state=model.getState();
    }

    public void bindRemoteView(RemoteView remoteView){
        this.remoteView=remoteView;

        try{
            remoteView.setId(player.getId());
        }catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    //da ViewProxy
    @Override
    public void notifyNextParameter(Response response) {
        try {
            remoteView.nextParameter(response);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMove(Player player, Cell source, Cell target) {
        try {
            switch (source.getType()) {
                case WINDOW_FRAME_CELL :
                    if (target.getType() == ModelType.WINDOW_FRAME_CELL)
                        remoteView.move(player.getId(), Response.WINDOW_FRAME_CELL, Response.WINDOW_FRAME_CELL, ((WindowFrameCell) source).getRow(), ((WindowFrameCell) source).getColumnn(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn());
                    break;
                case DRAFT_POOL_CELL:
                    if (target.getType() == ModelType.WINDOW_FRAME_CELL)
                        remoteView.move(player.getId(), Response.DRAFT_POOL_CELL, Response.WINDOW_FRAME_CELL, ((DraftPoolCell) source).getIndex(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn());
                    break;
                case ROUND_TRACK_CELL:
                    if (target.getType() == ModelType.WINDOW_FRAME_CELL)
                        remoteView.move(player.getId(), Response.ROUND_TRACK_CELL, Response.WINDOW_FRAME_CELL, ((RoundTrackCell) source).getRound(), ((RoundTrackCell) source).getIndex(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn());
                    break;
                default:
                    break;
            }
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateCellChangement(Player player, Cell cell) {
        try {
            switch (cell.getType()) {
                case WINDOW_FRAME_CELL:
                    remoteView.updateCell(player.getId(), Response.WINDOW_FRAME_CELL, ((WindowFrameCell) cell).getRow(), ((WindowFrameCell) cell).getColumnn(), cell.getDice().getValue(), cell.getDice().getColor().asChar());
                    break;
                case DRAFT_POOL_CELL:
                    remoteView.updateCell(player.getId(), Response.DRAFT_POOL_CELL, ((DraftPoolCell) cell).getIndex(), cell.getDice().getValue(), cell.getDice().getColor().asChar());
                    break;
                case ROUND_TRACK_CELL:
                    remoteView.updateCell(player.getId(), Response.ROUND_TRACK_CELL, ((RoundTrackCell) cell).getRound(), ((RoundTrackCell) cell).getIndex(), cell.getDice().getValue(), cell.getDice().getColor().asChar());
                    break;
                default:
                    break;
            }
        }catch(RemoteException e){
            e.printStackTrace();
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
        try {
            remoteView.refilledDraftPool(values, colors);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateToolCards(ToolCard[] toolCards) {
        int[] cards=new int[toolCards.length];
        for(int i=0; i<toolCards.length; i++)
            cards[i]=toolCards[i].getNumber();
        try {
            remoteView.loadToolCards(cards);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards) {
        int[] cards= new int[publicObjectiveCards.length];
        for(int i=0; i<cards.length; i++)
            cards[i]=publicObjectiveCards[i].getNumber();
        try {
            remoteView.loadPublicObjectiveCards(cards);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateWindowFrameChoices(WindowFrameList[] windowFrameLists) {
        controller.windowFrameChoice(windowFrameLists);
        int[] favorTokens= new int[windowFrameLists.length];
        String[] reps=new String[favorTokens.length];
        for(int i=0; i<windowFrameLists.length; i++){
            try {
                favorTokens[i] = windowFrameLists[i].getFavorToken();
                reps[i] = windowFrameLists[i].getRep();
            } catch(NullPointerException e){
                System.out.println(i);
                System.out.println(windowFrameLists[i]);
            }
        }
        try {
            remoteView.loadWindowFrameChoice(reps, favorTokens);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePlayers(Player[] players)  {
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
        try {
            remoteView.loadPlayers(names, ids, windowFrameReps, windowFrameFavorTokens);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateToolCardUsed(Player player, ToolCard toolCard, int tokens) {
        int index=-1;
        for(int i=0; i<state.getToolCards().length; i++){
            if(state.getToolCards()[i].equals(toolCard))
                index=i;
        }
        if(index>-1) {
            try {
                remoteView.toolCardUsed(player.getId(), index, tokens);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updatePrivateObjectiveCard(PrivateObjectiveCard card) {
        player.setPrivateObjectiveCard(card);
        try {
            remoteView.loadPrivateObjectiveCard(card.getColor().asChar());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStartTurn(Player player)  {
        try {
            remoteView.newTurn(player.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDiceDraw(Player player, Color color)  {
        try {
            remoteView.notifyDiceDraw(player.getId(), color.asChar());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateRoundTrack(int round, Cell[] cells) {
        int[] values = new int[cells.length];
        char[] colors=new char[cells.length];
        for(int i=0; i<cells.length; i++){
            values[i]=cells[i].getDice().getValue();
            colors[i]=cells[i].getDice().getColor().asChar();
        }

        try{
            remoteView.updateRoundTrack(round, values, colors);
        }catch(RemoteException e){
            e.printStackTrace();
        }

    }


/*
=======================================================================================================================
    da RemoteController
*/

    @Override
    public void command(GameCommand gameCommand) throws RemoteException{
        new Thread( () -> {
        try {
            switch (gameCommand.getType()) {
                case DRAFT_POOL_CELL:
                    controller.selectObject(state.getDraftPool().getCell(gameCommand.getX()));
                    break;
                case TOOL_CARD:
                    controller.selectObject(state.getToolCard(gameCommand.getX()));
                    break;
                case CHOICE:
                    controller.selectObject(new Choice(gameCommand.getX()));
                    break;
                case END_TURN:
                    controller.endTurn();
                    break;
                case WINDOW_FRAME_CELL:
                    controller.selectObject(player.getWindowFrame().getCell(gameCommand.getX(), gameCommand.getY()));
                    break;
                case ROUND_TRACK_CELL:
                    controller.selectObject(state.getRoundTrack().getRoundSet(gameCommand.getX()).get(gameCommand.getY()));
                    break;
                default:
                    return;
            }
        }catch (InvalidMoveException e){
            System.out.println("error");
        }}).start();
    }
    @Override
    public int getId(){
        return player.getId();
    }
    @Override
    public void command(Response type) throws RemoteException {
        switch(type){
            case END_TURN:
                controller.endTurn();
                break;
            default:
                break;
        }
    }
    @Override
    public void command(Response type, int index) throws InvalidMoveException, RemoteException {
        switch(type){
            case DRAFT_POOL_CELL:
                controller.selectObject(state.getDraftPool().getCell(index));
                break;
            case TOOL_CARD:
                controller.selectObject(state.getToolCard(index));
                break;
            case CHOICE:
                controller.selectObject(new Choice(index));
                break;
            default:
                break;
        }

    }
    @Override
    public void command(Response type, int param1, int param2) throws InvalidMoveException, RemoteException {
        switch(type){
            case WINDOW_FRAME_CELL:
                controller.selectObject(player.getWindowFrame().getCell(param1, param2));
                break;
            case ROUND_TRACK_CELL:
                controller.selectObject(state.getRoundTrack().getRoundSet(param1).get(param2));
                break;
            default:
                break;
        }
    }

}
