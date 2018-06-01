package server.viewproxy;

import common.RemoteMVC.RemoteController;
import common.RemoteMVC.RemoteView;
import common.command.GameCommand;
import common.exceptions.InvalidMoveException;
import common.exceptions.WrongParameter;
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
import server.model.state.player.Points;
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

    synchronized public void bindRemoteView(RemoteView remoteView){
        this.remoteView=remoteView;

        try{
            remoteView.setId(player.getId());
        }catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    //da ViewProxy
    @Override
    synchronized public void notifyNextParameter(Response response) {
        try {
            remoteView.nextParameter(response);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void notifyError(String message) {
        try {
            remoteView.error(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void notifyWrongParameter(String message) {
        try {
            remoteView.wrongParameter(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void updateMove(Player player, Cell source, Cell target) {
        try {
            switch (source.getType()) {
                case WINDOW_FRAME_CELL :
                    if (target.getType() == ModelType.WINDOW_FRAME_CELL)
                        remoteView.move(player.getId(), Response.WINDOW_FRAME_CELL, Response.WINDOW_FRAME_CELL, ((WindowFrameCell) source).getRow(), ((WindowFrameCell) source).getColumnn(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn());
                    break;
                case DRAFT_POOL_CELL:
                    if (target.getType() == ModelType.WINDOW_FRAME_CELL)
                        remoteView.move(player.getId(), Response.DRAFT_POOL_CELL, Response.WINDOW_FRAME_CELL, ((DraftPoolCell) source).getIndex(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn());
                    else if(target.getType() == ModelType.ROUND_TRACK_CELL)
                        remoteView.move(player.getId(), Response.DRAFT_POOL_CELL, Response.ROUND_TRACK_CELL, ((DraftPoolCell) source).getIndex(), ((RoundTrackCell) target).getRound(), ((RoundTrackCell) target).getIndex());
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
    synchronized public void updateCellChangement(Player player, Cell cell) {
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
    synchronized public void updateRefillDraftPool(Cell[] draftPool) {
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
    synchronized public void updateToolCards(ToolCard[] toolCards) {
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
    synchronized public void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards) {
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
    synchronized public void updateWindowFrameChoices(WindowFrameList[] windowFrameLists) {
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
    synchronized public void updatePlayers(Player[] players)  {
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
    synchronized public void updateToolCardUsed(Player player, ToolCard toolCard, int tokens) {
        int index=-1;
        for(int i=0; i<state.getToolCards().size(); i++){
            if(state.getToolCards().get(i).equals(toolCard))
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
    synchronized public void updatePrivateObjectiveCard(PrivateObjectiveCard card) {
        player.setPrivateObjectiveCard(card);
        try {
            remoteView.loadPrivateObjectiveCard(card.getColor().asChar());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void updateStartTurn(Player player)  {
        try {
            remoteView.newTurn(player.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void updateDiceDraw(Player player, Color color)  {
        try {
            remoteView.notifyDiceDraw(player.getId(), color.asChar());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    synchronized public void updateRoundTrack(int round, Cell[] cells) {
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

    @Override
    synchronized public void updateEndGame(Player[] scoreboard){
        int[] scoreboardIds = new int[scoreboard.length];
        char[] charCards=new char[scoreboard.length];
        int[][] matrixPoins=new int[scoreboard.length][7];
        for(int i=0; i<scoreboard.length; i++){
            Points points = scoreboard[i].getPoints();
            scoreboardIds[i]=scoreboard[i].getId();
            charCards[scoreboard[i].getId()] = scoreboard[i].getPrivateObjectiveCard().getColor().asChar();
            matrixPoins[scoreboard[i].getId()][0]=points.getPointsFromPublicCard(0);
            matrixPoins[scoreboard[i].getId()][1]=points.getPointsFromPublicCard(1);
            matrixPoins[scoreboard[i].getId()][2]=points.getPointsFromPublicCard(2);
            matrixPoins[scoreboard[i].getId()][3]=points.getPointsFromPrivateCard();
            matrixPoins[scoreboard[i].getId()][4]=points.getPointsFromFavorTokens();
            matrixPoins[scoreboard[i].getId()][5]=points.getPointsFromEmptyCells();
            matrixPoins[scoreboard[i].getId()][6]=points.getFinalPoints();
        }
        try {
            remoteView.endGame(charCards, scoreboardIds, matrixPoins);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void updateMutableData() {
        int[] draftPoolValues = new int[state.getDraftPool().getSize()];
        char[] draftPoolColors = new char[state.getDraftPool().getSize()];
        int[][] roundTrackValues = new int[state.getRoundTrack().getRound()][];
        char[][] roundTrackColors = new char[state.getRoundTrack().getRound()][];
        String[] names = new String[state.getPlayers().size()];
        int[] ids = new int[state.getPlayers().size()];
        int[] favorTokens = new int[state.getPlayers().size()];
        String[] windowFrameReps = new String[state.getPlayers().size()];
        int[][][] windowFrameValues = new int[state.getPlayers().size()][4][5];
        char[][][] windowFrameColors = new char[state.getPlayers().size()][4][5];

        for(int i=0; i<state.getDraftPool().getSize(); i++){
            DraftPoolCell draftPoolCell = state.getDraftPool().getCell(i);
            if(draftPoolCell.isEmpty())
                draftPoolValues[i]=0;
            else{
                draftPoolValues[i]=draftPoolCell.getDice().getValue();
                draftPoolColors[i]=draftPoolCell.getDice().getColor().asChar();
            }
        }

        for(int i=1; i<state.getRoundTrack().getRound(); i++) {
            roundTrackValues[i] = new int[state.getRoundTrack().getRoundSet(i).size()];
            roundTrackColors[i] = new char[state.getRoundTrack().getRoundSet(i).size()];
            for (int j = 0; j < state.getRoundTrack().getRoundSet(i).size(); j++) {
                roundTrackColors[i][j]=state.getRoundTrack().getRoundSet(i).get(j).getDice().getColor().asChar();
                roundTrackValues[i][j]=state.getRoundTrack().getRoundSet(i).get(j).getDice().getValue();
            }
        }

        for(int i=0; i<state.getPlayers().size(); i++){
            names[i]=state.getPlayers().get(i).getName();
            ids[i]=state.getPlayers().get(i).getId();
            favorTokens[i]=state.getPlayers().get(i).getFavorTokens();
            windowFrameReps[i]=state.getPlayers().get(i).getWindowFrame().getRep();
            for(int h=0; h<4; h++){
                for(int k=0; k<5; k++){
                    WindowFrameCell windowFrameCell = state.getPlayers().get(i).getWindowFrame().getCell(h,k);
                    if(windowFrameCell.isEmpty())
                        windowFrameValues[i][h][k] = 0;
                    else{
                        windowFrameValues[i][h][k] =  windowFrameCell.getDice().getValue();
                        windowFrameColors[i][h][k] =  windowFrameCell.getDice().getColor().asChar();
                    }

                }
            }
        }
        try {
            remoteView.mutableData(draftPoolValues, draftPoolColors, roundTrackValues, roundTrackColors, names, ids, favorTokens, windowFrameReps, windowFrameValues, windowFrameColors );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void updateReinsertPlayer(Player player) {
        try {
            remoteView.reinsertPlayer(player.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void updateSuspendPlayer(Player player) {
        try {
            remoteView.suspendPlayer(player.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    synchronized public void updateToolCardsChoice() {
        try {
            remoteView.toolCardsChoice();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    synchronized public void updateRemovedDice(Player player, DraftPoolCell cell) {
        try {
            remoteView.removeDice(player.getId(), Response.DRAFT_POOL_CELL, cell.getIndex());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


/*
=======================================================================================================================
    da RemoteController
*/

    @Override
    synchronized public void command(GameCommand gameCommand) {
        new Thread( () -> {
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
                    controller.selectObject(player.getWindowFrame());
                    controller.selectObject(player.getWindowFrame().getCell(gameCommand.getX(), gameCommand.getY()));
                    break;
                case ROUND_TRACK_CELL:
                    controller.selectObject(state.getRoundTrack().getRoundSet(gameCommand.getX()).get(gameCommand.getY()));
                    break;
                case SIMPLE_MOVE_REQUEST:
                    controller.isDiceMove();
                    break;
                case ACTIVE_AGAIN:
                    controller.reinsertPlayer();
                    break;
                default:
                    return;
            }
        }).start();
    }
    @Override
    synchronized public int getId(){
        return player.getId();
    }

    @Override
    synchronized public void command(Response type) {
        new Thread( () -> {
            switch (type) {
                case END_TURN:
                    controller.endTurn();
                    break;
                case ACTIVE_AGAIN:
                    controller.reinsertPlayer();
                    break;
                default:
                    break;
            }
        }).start();
    }
    @Override
    synchronized public void command(Response type, int index) {
        new Thread( () -> {
            switch (type) {
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
        }).start();

    }
    @Override
    synchronized public void command(Response type, int param1, int param2) {
        new Thread( () -> {
            switch (type) {
                case WINDOW_FRAME_CELL:
                    controller.selectObject(player.getWindowFrame());
                    controller.selectObject(player.getWindowFrame().getCell(param1, param2));
                    break;
                case ROUND_TRACK_CELL:
                    controller.selectObject(state.getRoundTrack().getRoundSet(param1).get(param2));
                    break;
                default:
                    break;
            }
        }).start();
    }

}
