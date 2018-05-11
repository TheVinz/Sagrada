package server;

import common.exceptions.InvalidMoveException;
import server.observer.Observable;
import server.observer.Observer;
import server.state.State;
import server.state.boards.Cell;
import server.state.boards.draftpool.DraftPoolCell;
import server.state.dice.Dice;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.player.Player;
import server.state.toolcards.ToolCard;
import server.state.utilities.Util;

import java.util.ArrayList;
import java.util.List;

public class Model implements Observable {
    private List<Observer> observers;

    private State state;

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
    public ViewProxy addPlayer(String name) throws Exception {
        if(state.getPlayers().size()==4) throw new Exception("The game is full");
        else {
            int id=state.getPlayers().size();
            state.addPlayer(name, id, this);
            ViewProxy o=new ViewProxy(this, id);
            addObserver(o);
            return o;
        }
    }

    //TODO introdurre gestione dei turni

    public void move(Player player, Cell source, Cell target) throws InvalidMoveException {
        source.move(target);
        player.setDiceMoved(true);
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
    public void endRound() throws Exception {
        state.getRoundTrack().endRound(state.getDraftPool());
    }
    public void startRound() throws InvalidMoveException{
        state.getDraftPool().draw(state.getBag());
        notifyRefillDraftPool(state.getDraftPool().getDraftPool().toArray(new Cell[0]));
    }

    //TODO introdurre codice per i favor tokens
    public void toolCardUsed(Player player, ToolCard toolCard){
        /*
         * Codice che aggiorna i favor tokens...
         * ...
         * ...
         * ...
         * */
        notifyToolCardUsed(player, toolCard);
    }

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
        for(Observer o:observers) o.updateMove(source, target);
    }

    @Override
    public void notifyCellChangement(Player player, Cell cell) {
        for(Observer o:observers) o.updateCellChangement(cell);
    }

    @Override
    public void notifyRefillDraftPool(Cell[] draftPool) {
        for(Observer o:observers) o.updateRefillDraftPool(draftPool);
    }

    @Override
    public void notifyToolCards() {
        for(Observer o:observers) o.updateToolCards(state.getToolCards());
    }

    @Override
    public void notifyObjectiveCards(PublicObjectiveCard[] publicObjectiveCards) {
        for(Observer o:observers) o.updateObjectiveCards(publicObjectiveCards);
    }

    @Override
    public void notifyWindowFrameChoices() {
        for(Observer o:observers) o.updateWindowFrameChoices(Util.getWindowFrameChoiche());
    }

    @Override
    public void notifyPlayers(Player[] players) {
        for(Observer o:observers) o.updatePlayers(players);
    }

    @Override
    public void notifyToolCardUsed(Player player, ToolCard toolCard) {
        for(Observer o:observers) o.updateToolCardUsed(player, toolCard);
    }

    @Override
    public void notifyDraw(Player player, Dice dice){
        for(Observer o : observers) o.updateDiceDraw(player, dice.getColor());
    }

    @Override
    public void notifyPrivateObjectiveCard(){
        for(Observer o:observers) o.updatePrivateObjectiveCard(Util.getCard());
    }

    @Override
    public void notifyStartTurn(Observer o) {
        o.updateStartTurn();
    }
}
