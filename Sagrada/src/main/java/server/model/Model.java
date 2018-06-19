package server.model;

import common.exceptions.InvalidMoveException;
import server.GameManager;
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
import server.viewproxy.ViewProxy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Model implements Observable {

    private Map<Player, Observer> playerObserverMap = new ConcurrentHashMap<>();

    private final State state;
    private final Util util;
    private RoundManager roundManager;
    private GameManager gameManager;
    private boolean acceptPlayers = true;
    protected boolean started = false;

    public Model(GameManager gameManager){
        this.gameManager = gameManager;
        state = new State(this);
        util = new Util();
    }


    public State getState() {
        return this.state;
    }

    public Util getUtil() {return this.util;}


    public boolean isSingleplayer(){
        return false;
    }

    public void addViewProxyPlayer(ViewProxy viewProxy, Player player){

            if(playerObserverMap.keySet().contains(player)) {
                playerObserverMap.replace(player, viewProxy);
                reinsertPlayer(player);
            }
            else {
                playerObserverMap.put(player, viewProxy);
                if(state.getPlayers().size() == 4) gameManager.startGame(this);
            }
    }

    public synchronized Player addPlayer(String name) {
        Player player;

        for(Player p : state.getPlayers()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        //if(state.getPlayers().size()==4) throw new Exception("The game is full");


        player = new Player(name, state.getPlayers().size());
        state.addPlayer(player);
        return player;
    }

    public synchronized void removePlayer(Player player){
        playerObserverMap.remove(player);
        state.getPlayers().remove(player);
        gameManager.removePlayer(player.getName());
    }

    /*
    * =================================================================================================================
    * Game routine
    */
    public synchronized void init(){
        if(started)
            return;
        for(ToolCard t : util.getToolCards(this, 3))
            state.getToolCards().add(t);
        for(PublicObjectiveCard p : util.getPublicObjectiveCards(false))
            state.getPublicObjectiveCards().add(p);
        notifyToolCards();
        notifyObjectiveCards();
        notifyPlayers(state.getPlayers().toArray(new Player[0]));
        started = true;
        startRound();
    }

    public void startGame() {
        acceptPlayers = false;
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
            e.printStackTrace();
            System.exit(1);
        }
        notifyRefillDraftPool(state.getDraftPool().getDraftPool().toArray(new Cell[0]));
        roundManager.startRound();
        Player active = roundManager.next();
        if(active.isSuspended()){
            endTurn(active);
        }
        else {
            active.setActive();
            notifyStartTurn(active);
        }
    }

    public void endTurn(Player player) {
        Player active = player;
        active.endTurn();
        if(!state.isGameFinished() && started) {
            if (roundManager.hasNext()) {
                active = roundManager.next();
                active.setActive();
                if(active.isSuspended() || active.isJumpSecondTurn())
                    endTurn(active);
                else
                    notifyStartTurn(active);
            } else endRound();
        }
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
    public void endGame() {
        state.setGameFinished(true);
        List <Player> scoreboard = new ArrayList<>();
        for (Player player : state.getPlayers()) {
            player.calculatePoints(state);
            scoreboard.add(player);
        }
        scoreboard.sort(new PointsComparator());
        notifyEndGame(scoreboard.toArray(new Player[0]));
        Player winner = getWinner(scoreboard);
        notifyGameManager("Winner: "+winner.getName());
        this.playerObserverMap = null;
    }

    private Player getWinner(List<Player> scoreboard){
        return scoreboard.stream()
                .filter( player -> !player.isSuspended())
                .findFirst()
                .orElse(scoreboard.get(0));
    }

    public void notifyGameManager(String message){
        gameManager.endGame(this, message);
    }

    public synchronized void reinsertPlayer(Player player){
        player.setSuspended(false);
        Observer o = playerObserverMap.get(player);
        o.updateObjectiveCards(state.getPublicObjectiveCards().toArray(new PublicObjectiveCard[0]));
        o.updateToolCards(state.getToolCards().toArray(new ToolCard[0]));
        o.updateMutableData();
        o.updatePrivateObjectiveCard(player.getPrivateObjectiveCard());
        notifyReinsertPlayer(player);
    }



    public void suspendPlayer(Player player){
        if(acceptPlayers) {
            removePlayer(player);
            player.setSuspended(true);
            System.out.print(this.hashCode() + "\n");
            state.getPlayers().stream().forEach(p -> System.out.print("\t" + p.getName() + "\n"));
            System.out.print(">>>");
        }
        else if(!player.isSuspended() && started) {
            player.setSuspended(true);
            notifySuspendPlayer(player);
            int cont = 0;
            for (Player p : state.getPlayers()) {
                if (!p.isSuspended()) cont++;
            }
            if (cont < 2) endGame();
        }
    }


    /*
    * =================================================================================================================
    * Changement
    * */
    public void toolCardsChoice(int toolCards){
    }

    public void privateCardChoice(int card){

    }

    public void windowFrameChoice(Player player, WindowFrameList windowFrameList){
        player.setWindowFrame(windowFrameList);
        player.setInactive();
        for(Player p : state.getPlayers()){
            if (p.getWindowFrame() == null) return;
        }
        if(!state.isGameFinished()) init();
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
    }

    @Override
    public void removeObserver(Observer o) {
    }

    @Override
    public void notifyMove(Player player, Cell source, Cell target) {
        for(Observer o: playerObserverMap.values()) o.updateMove(player, source, target);
    }

    @Override
    public void notifyCellChangement(Player player, Cell cell) {
        for(Observer o: playerObserverMap.values()) o.updateCellChangement(player, cell);
    }

    @Override
    public void notifyRefillDraftPool(Cell[] draftPool) {
        for(Observer o: playerObserverMap.values()) o.updateRefillDraftPool(draftPool);
    }

    @Override
    public void notifyToolCards() {
        for(Observer o: playerObserverMap.values())
            o.updateToolCards(state.getToolCards().toArray(new ToolCard[0]));
    }

    @Override
    public void notifyObjectiveCards() {
        for(Observer o: playerObserverMap.values())
            o.updateObjectiveCards(state.getPublicObjectiveCards().toArray(new PublicObjectiveCard[0]));
    }

    @Override
    public void notifyWindowFrameChoices() {
        for(Observer o: playerObserverMap.values()) {
            o.updateWindowFrameChoices(util.getWindowFrameChoice());
        }
    }

    @Override
    public void notifyPlayers(Player[] players) {
        for(Observer o: playerObserverMap.values()) o.updatePlayers(players);
    }

    @Override
    public void notifyToolCardUsed(Player player, ToolCard toolCard, int tokens) {
        for(Observer o: playerObserverMap.values()) o.updateToolCardUsed(player, toolCard, tokens);
    }

    @Override
    public void notifyDraw(Player player, Dice dice) {
        for(Observer o : playerObserverMap.values()) o.updateDiceDraw(player, dice.getColor());
    }

    @Override
    public void notifyPrivateObjectiveCard() {
        for(Observer o: playerObserverMap.values()) {
            o.updatePrivateObjectiveCard(util.getCard());
        }
    }

    @Override
    public void notifyStartTurn(Player player) {
        for(Observer o : playerObserverMap.values())
            o.updateStartTurn(player);
    }
    @Override
    public void notifyRoundTrackUpdate(int round, Cell[] cells){
        for(Observer o : playerObserverMap.values()) o.updateRoundTrack(round, cells);
    }

    @Override
    public void notifyEndGame(Player[] scoreboard){
        for (Observer o: playerObserverMap.values()) o.updateEndGame(scoreboard);
        for (Player p : state.getPlayers()) p.endGame();

    }

    @Override
    public void notifyReinsertPlayer(Player player) {
        for(Observer o : playerObserverMap.values())
            o.updateReinsertPlayer(player);
    }

    @Override
    public void notifySuspendPlayer(Player player){
        for(Observer o : playerObserverMap.values())
            o.updateSuspendPlayer(player);
    }

    @Override
    public void notifyRemovedDice(Player player, DraftPoolCell cell) {
        for(Observer o : playerObserverMap.values())
            o.updateRemovedDice(player, cell);
    }


}
