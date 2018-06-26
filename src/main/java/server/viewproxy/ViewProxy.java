package server.viewproxy;
import common.Notification;
import common.RemoteMVC.RemoteController;
import common.command.GameCommand;
import common.response.Response;
import common.viewchangement.*;
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
import server.observer.Observer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public abstract class ViewProxy extends UnicastRemoteObject implements Observer, RemoteController {

    protected Player player;
    private State state;
    private Controller controller;
    private Model model;
    private boolean ping = true;

    SinglePlayerEndGame endGameDataSinglePlayer = null;
    EndGame endGameDataMultiPlayer = null;

    public ViewProxy() throws RemoteException{
        super();
    }

    /**
     * Send a changement of the state to the client.
     * @param changement the changement to send to the client.
     */
    abstract void change(Changement changement);

    /**
     * Notify the client about the mistake of his last action.
     * @param notification the notification to send to the client.
     */
    abstract void notify(Notification notification);

    /**
     * Inform the client about his next expected action in reaction to his last action.
     * @param response the response to send to the client
     */
    abstract void send(Response response);

    /**
     * Inform the client that he has been suspended by closing the connection.
     */
    public abstract void closeConnection();


    /**
     * Ping the client in order to know if he's still connected, otherwise he will be suspended.
     */
    public abstract void ping();

    /**
     * Set the model Player representing the remote player associated to this ViewProxy
     * @param player the remote player representation on the Model
     */
    public void setPlayer(Player player) throws Exception{
        this.player = player;
        this.controller = new Controller(model, player, this);
        change(new LoadId(player.getId()));
        new Thread(this::ping).start();
    }

    /**
     * Set the model associated to the game
     * @param model the Model of the game
     */
    public void setModel(Model model){
        this.model = model;
        this.state = model.getState();
    }

    /**
     * Suspend the associated player
     */
    public void suspendPlayer(){
        controller.timeFinished();
        controller.endTurn();
        ping = false;
    }

    //da ViewProxy
    @Override
    public void notifyNextParameter(Response response) {
        send(response);
        if(response.equals(Response.SUCCESS_USED_TOOL_CARD) || response.equals(Response.SUCCESS_MOVE_DONE) || response.equals(Response.SUCCESS_TOOL_CARD_WITH_MOVE))
            player.getTimer().start();
    }

    @Override
    public void notifyError(String message) {
            notify(new Notification(Notification.ERROR, message));
    }

    @Override
    public void notifyWrongParameter(String message) {
            notify(new Notification(Notification.WRONG_PARAMETER, message));
    }



    @Override
    public void updateMove(Player player, Cell source, Cell target) {
        switch (source.getType()) {
            case WINDOW_FRAME_CELL :
                if (target.getType() == ModelType.WINDOW_FRAME_CELL)
                    change(new Move(player.getId(), Response.WINDOW_FRAME_CELL, Response.WINDOW_FRAME_CELL, ((WindowFrameCell) source).getRow(), ((WindowFrameCell) source).getColumnn(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn()));
                break;
            case DRAFT_POOL_CELL:
                if (target.getType() == ModelType.WINDOW_FRAME_CELL)
                    change( new Move(player.getId(), Response.DRAFT_POOL_CELL, Response.WINDOW_FRAME_CELL, ((DraftPoolCell) source).getIndex(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn()));
                else if(target.getType() == ModelType.ROUND_TRACK_CELL)
                    change(new Move(player.getId(), Response.DRAFT_POOL_CELL, Response.ROUND_TRACK_CELL, ((DraftPoolCell) source).getIndex(), ((RoundTrackCell) target).getRound(), ((RoundTrackCell) target).getIndex()));
                break;
            case ROUND_TRACK_CELL:
                if (target.getType() == ModelType.WINDOW_FRAME_CELL)
                    change(new Move(player.getId(), Response.ROUND_TRACK_CELL, Response.WINDOW_FRAME_CELL, ((RoundTrackCell) source).getRound(), ((RoundTrackCell) source).getIndex(), ((WindowFrameCell) target).getRow(), ((WindowFrameCell) target).getColumnn()));
                break;
            default:
                break;
        }
    }

    @Override
    public void updateCellChangement(Player player, Cell cell) {
        switch (cell.getType()) {
            case WINDOW_FRAME_CELL:
                change(new CellUpdate(player.getId(), Response.WINDOW_FRAME_CELL, ((WindowFrameCell) cell).getRow(), ((WindowFrameCell) cell).getColumnn(), cell.getDice().getValue(), cell.getDice().getColor().asChar()));
                break;
            case DRAFT_POOL_CELL:
                change(new CellUpdate(player.getId(), Response.DRAFT_POOL_CELL, ((DraftPoolCell) cell).getIndex(), cell.getDice().getValue(), cell.getDice().getColor().asChar()));
                break;
            case ROUND_TRACK_CELL:
                change(new CellUpdate(player.getId(), Response.ROUND_TRACK_CELL, ((RoundTrackCell) cell).getRound(), ((RoundTrackCell) cell).getIndex(), cell.getDice().getValue(), cell.getDice().getColor().asChar()));
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
        change(new RefilledDraftPool(values, colors));
    }

    @Override
    public void updateToolCards(ToolCard[] toolCards) {
        int[] cards=new int[toolCards.length];
        for(int i=0; i<toolCards.length; i++)
            cards[i]=toolCards[i].getNumber();
        change(new LoadToolCards(cards));
    }

    @Override
    public void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards) {
        int[] cards= new int[publicObjectiveCards.length];
        for(int i=0; i<cards.length; i++)
            cards[i]=publicObjectiveCards[i].getNumber();
        change(new LoadPublicObjectiveCards(cards));
    }

    @Override
    public void updateWindowFrameChoices(WindowFrameList[] windowFrameLists) {
        controller.windowFrameChoice(windowFrameLists);
        int[] favorTokens= new int[windowFrameLists.length];
        String[] reps=new String[favorTokens.length];
        for(int i=0; i<windowFrameLists.length; i++){
            favorTokens[i] = windowFrameLists[i].getFavorToken();
            reps[i] = windowFrameLists[i].getRep();
        }
        change(new WindowFrameChoices(reps, favorTokens));
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
        change(new LoadPlayers(names, ids, windowFrameReps, windowFrameFavorTokens));
    }

    @Override
    public void updateToolCardUsed(Player player, ToolCard toolCard, int tokens) {
        int index=-1;
        for(int i=0; i<state.getToolCards().size(); i++){
            if(state.getToolCards().get(i).equals(toolCard))
                index=i;
        }
        if(index>-1) {
            change(new ToolCardUsed(player.getId(), index, tokens));
        }
    }

    @Override
    public void updatePrivateObjectiveCard(PrivateObjectiveCard card) {
        player.setPrivateObjectiveCard(card);
        change(new LoadPrivateObjectiveCard(card.getColor().asChar()));
    }

    @Override
    public void updateStartTurn(Player player)  {
        change(new NewTurn(player.getId()));
    }

    @Override
    public void updateDiceDraw(Player player, Color color)  {
        change(new DiceDraw(player.getId(), color.asChar()));
    }
    @Override
    public void updateRoundTrack(int round, Cell[] cells) {
        int[] values = new int[cells.length];
        char[] colors=new char[cells.length];
        for(int i=0; i<cells.length; i++){
            values[i]=cells[i].getDice().getValue();
            colors[i]=cells[i].getDice().getColor().asChar();
        }
        change(new LoadLastRoundTrack(round, values, colors));
    }

    @Override
    public void updateEndGame(Player[] scoreboard){
        char[] charCards=new char[scoreboard.length];
        int[] scoreboardIds = new int[scoreboard.length];
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
        endGameDataMultiPlayer = new EndGame(charCards, scoreboardIds, matrixPoins);
        if(!player.isSuspended()) {
            change(endGameDataMultiPlayer);
        }
    }

    @Override
    public void updateMutableData() {
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

        change(new MutableData(draftPoolValues, draftPoolColors, roundTrackValues, roundTrackColors, names, ids, favorTokens, windowFrameReps, windowFrameValues, windowFrameColors, player.getId()));
    }

    @Override
    public void updateReinsertPlayer(Player player) {
        change(new ReinsertedPlayer(player.getId()));
    }

    @Override
    public void updateSuspendPlayer(Player player) {
        if(player.equals(this.player))
            closeConnection();
        else
            change(new SuspendedPlayer(player.getId()));
    }

    @Override
    public void updateToolCardsChoice() {
        if(!player.isSuspended()) change(new ToolCardsChoices());
        else controller.selectObject(new Choice(0));
    }

    @Override
    public void updateRemovedDice(Player player, DraftPoolCell cell) {
        change(new RemovedDice(player.getId(), Response.DRAFT_POOL_CELL, cell.getIndex()));
    }

    @Override
    public void updatePrivateObjectiveCardChoice() {
        if(!player.isSuspended()) change(new PrivateObjectiveCardsChoice(state.getPlayer(player.getId()).getPrivateObjectiveCard(0).getColor().asChar(), state.getPlayer(player.getId()).getPrivateObjectiveCard(1).getColor().asChar()));
        else controller.selectObject(new Choice(0));
    }

    @Override
    public void updateSinglePlayerEndGame(int targetPoints, Points points, PrivateObjectiveCard privateObjectiveCard){
        int[] vectorPoints = new int[5];
        vectorPoints[0] = points.getPointsFromPublicCard(0);
        vectorPoints[1] = points.getPointsFromPublicCard(1);
        vectorPoints[2] = points.getPointsFromPrivateCard();
        vectorPoints[3] = points.getPointsFromEmptyCells();
        vectorPoints[4] = points.getFinalPoints();

        endGameDataSinglePlayer = new SinglePlayerEndGame(targetPoints, vectorPoints, privateObjectiveCard.getColor().asChar());
        if(!player.isSuspended()) change(endGameDataSinglePlayer);
    }


/*
=======================================================================================================================
    da RemoteController
*/

    @Override
    public void command(GameCommand gameCommand) {
        if(gameCommand == null)
            return;
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
            default:
        }
    }

    /**
     * @return true if the player is still active and must be pinged
     */
    public boolean isPing() {
        return ping;
    }

    /**
     * Set the ping value
     * @param ping true if the player should be pinged
     */
    public void setPing(boolean ping) {
        this.ping = ping;
    }
}
