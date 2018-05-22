package server.model;

import common.exceptions.InvalidMoveException;
import server.model.state.boards.windowframe.WindowFrameList;
import server.observer.Observable;
import server.observer.Observer;
import server.model.state.RoundManager;
import server.model.state.State;
import server.model.state.boards.Cell;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Util;
import server.viewproxy.RMIViewProxy;

import java.util.ArrayList;
import java.util.List;

public class Model implements Observable {
    private List<Observer> observers;

    private State state;
    private RoundManager roundManager;

    public Model(){
        state=new State(this);
        observers=new ArrayList<>();
    }


    public State getState() {
        return state;
    }


    /*
    Il ritorno della view proxy ignoratelo, servirà poi quando introdurremo la rete
    */
    public RMIViewProxy addRMIPlayer(String name) throws Exception {
        if(state.getPlayers().size()==4) throw new Exception("The game is full");
        else {
            int id=state.getPlayers().size();
            Player player=new Player(name, id);
            state.addPlayer(player);
            RMIViewProxy o=new RMIViewProxy(this, player);
            addObserver(o);
            return o;
        }
    }

    /*
    * =================================================================================================================
    * Game routine
    */
    public void startGame() {
        notifyObjectiveCards();
        notifyPrivateObjectiveCard();
        notifyToolCards();
        for(Player p : state.getPlayers()) p.setActive();
        roundManager=new RoundManager(state.getPlayers());
        notifyWindowFrameChoices();

    }

    private void startRound() {
        try {
            state.getDraftPool().draw(state.getBag());
        }
        catch(InvalidMoveException e){
            System.err.println("Error on Model.startRound()");
            System.exit(1);
        }
        notifyRefillDraftPool(state.getDraftPool().getDraftPool().toArray(new Cell[0]));
        roundManager.startRound();
        Player active= roundManager.next();
        active.setActive();
        notifyStartTurn(active);
    }
    public void endTurn(Player player) {
        player.endTurn();
        if(roundManager.hasNext()) {
            Player active = roundManager.next();
            active.setActive();
            notifyStartTurn(active);
        }
        else endRound();
    }
    private void endRound() {
        try {
            state.getRoundTrack().endRound(state.getDraftPool());
        } catch (Exception e) {
            System.err.println("Error on Model.endRound()");
            System.exit(1);
        }
        for(Player player : state.getPlayers()) player.endRound();
        notifyRoundTrackUpdate(state.getRoundTrack().getRound()-1, state.getRoundTrack().getRoundSet(state.getRoundTrack().getRound()-1).toArray(new Cell[0]));
        if(state.getRoundTrack().getRound() > RoundTrack.MAX_ROUND)
            endGame();
        else startRound();
    }
    private void endGame(){
        /*Da definire*/
    }

    /*
    * =================================================================================================================
    * Changement
    * */

    public void windowFrameChoice(Player player, WindowFrameList windowFrameList){
        player.setWindowFrame(windowFrameList);
        player.endTurn();
        for(Player p : state.getPlayers()){
            if (p.getWindowFrame() == null) return;
        }
        notifyPlayers(state.getPlayers().toArray(new Player[0]));
        startRound();
    }
    public void move(Player player, Cell source, Cell target) throws InvalidMoveException {
        source.move(target);
        player.setDiceMoved();
        notifyMove(player, source, target);
    }

    public void exchange(Player player, Cell first, Cell second) throws InvalidMoveException {
        Dice dice= second.removeDice();
        move(player, first, second);
        putDice(player, dice, first);
    }

    public void putDice(Player player, Dice dice, Cell target) throws InvalidMoveException {
        target.put(dice);
        notifyCellChangement(player, target);
    }

    public void increase(Player player, Cell cell) throws InvalidMoveException {
        cell.getDice().increase();
        notifyCellChangement(player, cell);
    }

    public void decrease(Player player, Cell cell) throws InvalidMoveException {
        cell.getDice().decrease();
        notifyCellChangement(player, cell);
    }

    public Dice drawDice(Player player) {
        Dice dice=state.getBag().draw();
        notifyDraw(player, dice);
        return dice;
    }

    public void flipDice(Player player, Cell cell) {
        cell.getDice().flip();
        notifyCellChangement(player, cell);
    }

    public void toolCardUsed(Player player, ToolCard toolCard) {
        int tokens;
        if(!toolCard.isUsed()){
            tokens=1;
            toolCard.setUsed();
        }
        else tokens=2;
        player.removeFaforTokens(tokens);
        player.setToolCardUsed();
        notifyToolCardUsed(player, toolCard, tokens);
    }

    /*=================================================================================================================
    * da Observable
    * */

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyMove(Player player, Cell source, Cell target) {
        for(Observer o:observers) o.updateMove(player, source, target);
    }

    @Override
    public void notifyCellChangement(Player player, Cell cell) {
        for(Observer o:observers) o.updateCellChangement(player, cell);
    }

    @Override
    public void notifyRefillDraftPool(Cell[] draftPool) {
        for(Observer o:observers) o.updateRefillDraftPool(draftPool);
    }

    @Override
    public void notifyToolCards() {

        for(Observer o:observers)
            o.updateToolCards(state.getToolCards());
    }

    @Override
    public void notifyObjectiveCards() {
        for(Observer o:observers)
            o.updateObjectiveCards(state.getPublicObjectiveCards());
    }

    @Override
    public void notifyWindowFrameChoices() {
        for(Observer o:observers) {
            o.updateWindowFrameChoices(Util.getWindowFrameChoiche());
        }
    }

    @Override
    public void notifyPlayers(Player[] players) {
        for(Observer o:observers) o.updatePlayers(players);
    }

    @Override
    public void notifyToolCardUsed(Player player, ToolCard toolCard, int tokens) {
        for(Observer o:observers) o.updateToolCardUsed(player, toolCard, tokens);
    }

    @Override
    public void notifyDraw(Player player, Dice dice) {
        for(Observer o : observers) o.updateDiceDraw(player, dice.getColor());
    }

    @Override
    public void notifyPrivateObjectiveCard() {
        for(Observer o:observers)
            o.updatePrivateObjectiveCard(Util.getCard());
    }

    @Override
    public void notifyStartTurn(Player player) {
        for(Observer o : observers)
            o.updateStartTurn(player);
    }
    @Override
    public void notifyRoundTrackUpdate(int round, Cell[] cells){
        for(Observer o : observers) o.updateRoundTrack(round, cells);
    }
}
