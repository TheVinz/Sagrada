package server.model;

import common.exceptions.InvalidMoveException;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.utilities.PointsComparator;
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
import server.viewproxy.ViewProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model implements Observable {
    private List<Observer> activeObservers;
    private Map<Player, Observer> playerObserverMap = new HashMap<>();

    private final State state;
    private final Util util;
    private RoundManager roundManager;

    public Model(){
        state = new State(this);
        util = new Util();
        activeObservers =new ArrayList<>();
    }


    public State getState() {
        return this.state;
    }

    public Util getUtil() {return this.util;}


    public boolean isSingleplayer(){
        return false;
    }

    public void addViewProxyPlayer(ViewProxy viewProxy, Player player){
            addObserver(viewProxy);
            playerObserverMap.put(player, viewProxy);
    }

    public Player addPlayer(String name, int id) throws Exception{ //da fare synchronized nel caso più giocatori si connettano contemporaneamente?
        if(state.getPlayers().size()==4) throw new Exception("The game is full");
        Player player = new Player(name, id);
        state.addPlayer(player);
        return player;
    }

    /*
    * =================================================================================================================
    * Game routine
    */
    public void init(){
        for(ToolCard t : util.getToolCards(this, 3))
            state.getToolCards().add(t);
        for(PublicObjectiveCard p : util.getPublicObjectiveCards(false))
            state.getPublicObjectiveCards().add(p);
        notifyToolCards();
        notifyObjectiveCards();
        notifyPlayers(state.getPlayers().toArray(new Player[0]));
        startRound();
    }

    public void startGame() {
        notifyPrivateObjectiveCard();
        for(Player p : state.getPlayers()) p.setActive();
        roundManager=new RoundManager(state.getPlayers());
        notifyWindowFrameChoices();

    }

    public void startRound() {
        try {
            state.getDraftPool().draw(state.getBag());
        }
        catch(InvalidMoveException e){
            System.err.println("Error on Model.startRound()");
            System.exit(1);
        }
        notifyRefillDraftPool(state.getDraftPool().getDraftPool().toArray(new Cell[0]));
        roundManager.startRound();
        Player active;
        do {
            active = roundManager.next();
        } while(active.isSuspended()); //da controllare quando sono tutti sospesi
        active.setActive();
        notifyStartTurn(active);
    }
    public void endTurn(Player player) {
        Player active = player;
        do {
            active.endTurn();
            if(!roundManager.hasNext())
            {
                endRound();
                return;
            }
            active = roundManager.next();
        } while(active.isJumpSecondTurn()||active.isSuspended());
        active.setActive();
        notifyStartTurn(active);
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
    private void endGame() {
        List <Player> scoreboard = new ArrayList<>();
        for (Player player : state.getPlayers()) {
            player.calculatePoints(state);
            scoreboard.add(player);
        }
        scoreboard.sort(new PointsComparator());
        notifyEndGame(scoreboard.toArray(new Player[0]));
    }

    public void reinsertPlayer(Player player){
        player.setSuspended(false);
        Observer o = playerObserverMap.get(player);
        activeObservers.add(o);
        o.updateObjectiveCards(state.getPublicObjectiveCards().toArray(new PublicObjectiveCard[0]));
        o.updatePrivateObjectiveCard(player.getPrivateObjectiveCard());
        o.updateToolCards(state.getToolCards().toArray(new ToolCard[0]));
        o.updateMutableData();
        notifyReinsertPlayer(player);
    }



    public void suspendPlayer(Player player){
        player.setSuspended(true);
        activeObservers.remove(playerObserverMap.get(player));
        if(activeObservers.isEmpty())
            endGame();
        notifySuspendPlayer(player);
    }


    /*
    * =================================================================================================================
    * Changement
    * */
    public void toolCardsChoice(int toolCards){
    }

    public void windowFrameChoice(Player player, WindowFrameList windowFrameList){
        player.setWindowFrame(windowFrameList);
        player.setInactive();
        for(Player p : state.getPlayers()){
            if (p.getWindowFrame() == null) return;
        }
        init();
    }


    public void move(Player player, Cell source, Cell target) throws InvalidMoveException {
        source.move(target);
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

    public void flipDice(Player player, Cell cell) throws InvalidMoveException{
        cell.getDice().flip();
        notifyCellChangement(player, cell);
    }

    public void remove(Player player, DraftPoolCell cell) throws InvalidMoveException{
        Dice dice = cell.removeDice();
        //state.getBag().insert(dice);        ??? Il dado non viene rimosso dal gioco??
        notifyRemovedDice(player, cell);
    }

    public void toolCardUsed(Player player, ToolCard toolCard) {
        int tokens;
        if(!toolCard.isUsed()){
            tokens=1;
            toolCard.setUsed();
        }
        else tokens=2;
        player.removeFavorTokens(tokens);
        player.setToolCardUsed();
        notifyToolCardUsed(player, toolCard, tokens);
    }


    /*=================================================================================================================
    * da Observable
    * */

    @Override
    public void addObserver(Observer o) {
        activeObservers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        activeObservers.remove(o);
    }

    @Override
    public void notifyMove(Player player, Cell source, Cell target) {
        for(Observer o: activeObservers) o.updateMove(player, source, target);
    }

    @Override
    public void notifyCellChangement(Player player, Cell cell) {
        for(Observer o: activeObservers) o.updateCellChangement(player, cell);
    }

    @Override
    public void notifyRefillDraftPool(Cell[] draftPool) {
        for(Observer o: activeObservers) o.updateRefillDraftPool(draftPool);
    }

    @Override
    public void notifyToolCards() {

        for(Observer o: activeObservers)
            o.updateToolCards(state.getToolCards().toArray(new ToolCard[0]));
    }

    @Override
    public void notifyObjectiveCards() {
        for(Observer o: activeObservers)
            o.updateObjectiveCards(state.getPublicObjectiveCards().toArray(new PublicObjectiveCard[0]));
    }

    @Override
    public void notifyWindowFrameChoices() {
        for(Observer o: activeObservers) {
            o.updateWindowFrameChoices(util.getWindowFrameChoiche());
        }
    }

    @Override
    public void notifyPlayers(Player[] players) {
        for(Observer o: activeObservers) o.updatePlayers(players);
    }

    @Override
    public void notifyToolCardUsed(Player player, ToolCard toolCard, int tokens) {
        for(Observer o: activeObservers) o.updateToolCardUsed(player, toolCard, tokens);
    }

    @Override
    public void notifyDraw(Player player, Dice dice) {
        for(Observer o : activeObservers) o.updateDiceDraw(player, dice.getColor());
    }

    @Override
    public void notifyPrivateObjectiveCard() {
        for(Observer o: activeObservers)
            o.updatePrivateObjectiveCard(util.getCard());
    }

    @Override
    public void notifyStartTurn(Player player) {
        for(Observer o : activeObservers)
            o.updateStartTurn(player);
    }
    @Override
    public void notifyRoundTrackUpdate(int round, Cell[] cells){
        for(Observer o : activeObservers) o.updateRoundTrack(round, cells);
    }

    @Override
    public void notifyEndGame(Player[] scoreboard){
        for (Observer o: activeObservers) o.updateEndGame(scoreboard);
    }

    @Override
    public void notifyReinsertPlayer(Player player) {
        for(Observer o : activeObservers)
            o.updateReinsertPlayer(player);
    }

    @Override
    public void notifySuspendPlayer(Player player){
        for(Observer o : activeObservers)
            o.updateSuspendPlayer(player);
    }

    @Override
    public void notifyRemovedDice(Player player, DraftPoolCell cell) {
        for(Observer o : activeObservers)
            o.updateRemovedDice(player, cell);
    }


}
