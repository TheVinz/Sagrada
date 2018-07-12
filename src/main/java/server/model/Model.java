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

/**
 * The <tt>Model</tt> class of the MVC pattern. Each <tt>Model</tt> instance represents a different game.
 * This model contains a {@link State} attribute representing the actual game state and a map mapping each player to his
 * observer.
 */
public class Model implements Observable {

    private Map<Player, Observer> playerObserverMap = new ConcurrentHashMap<>();

    private final State state;
    private final Util util;
    private RoundManager roundManager;
    private GameManager gameManager;
    private boolean acceptPlayers = true;
    boolean started = false;

    /**
     * Initializes a <tt>Model</tt> instance setting the {@link GameManager} to be notified when game ends and instantiating
     * a new {@link Util} and a new {@link State}.
     * @param gameManager the GameManager this <tt>Model</tt> will notify.
     */
    public Model(GameManager gameManager){
        this.gameManager = gameManager;
        state = new State();
        util = new Util();
    }


    /**
     * Get method fot the game state.
     * @return the state of the game represented by this model.
     */
    public State getState() {
        return this.state;
    }

    /**
     * Get method for the game utils.
     * @return this game's utils.
     */
    public Util getUtil() {return this.util;}


    /**
     * This methods returns <code>true</code> in case this <tt>Model</tt> instance represents a single player game, <code>false</code>
     * otherwise.
     * @return <code>true</code> if this game is a single-player game, <code>false</code> otherwise.
     */
    public boolean isSingleplayer(){
        return false;
    }

    /**
     * Adds a new {@link ViewProxy} to the observers list and mapping it to the specified {@link Player}. If the Player
     * is in the map's key set yet the associated view proxy will be replaced by the new one.
     * @param viewProxy the view proxy handling the connection with the remote player.
     * @param player the model player representing the remote player associated with the ViewProxy.
     */
    public synchronized void addViewProxyPlayer(ViewProxy viewProxy, Player player){

        if(playerObserverMap.keySet().contains(player)) {
                playerObserverMap.replace(player, viewProxy);
                reinsertPlayer(player);
            }
            else {
                playerObserverMap.put(player, viewProxy);
                if(state.getPlayers().size() == 4) gameManager.startGame(this);
            }
    }

    /**
     * Inserts a new player to the player list in the state if there is no player with the indicated name, otherwise returns
     * the player with this username in the state's player list.
     * @param name the player's username.
     * @return a new player if there is no player in the state with this username, the player with this username otherwise.
     */
    public synchronized Player addPlayer(String name) {
        Player player;

        for(Player p : state.getPlayers()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        player = new Player(name, state.getPlayers().size());
        state.addPlayer(player);
        return player;
    }

    private synchronized void removePlayer(Player player){
        playerObserverMap.remove(player);
        state.getPlayers().remove(player);
        gameManager.removePlayer(player.getName());
    }

    /*
    * =================================================================================================================
    * Game routine
    */

    /**
     * Initializes the game if is not started yet, getting from the utils the {@link ToolCard}s and the {@link PublicObjectiveCard}s
     * associated to the starting game and notifies them to the players. Then the new game round routine starts.
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

    /**
     * Closes the accepting player phase and notifies to the playing ones their {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard}
     * and the {@link server.model.state.boards.windowframe.WindowFrame} choice.
     */
    public void startGame() {
        acceptPlayers = false;
        notifyPrivateObjectiveCard();
        for(Player p : state.getPlayers()) p.setActive();
        roundManager=new RoundManager(state.getPlayers());
        notifyWindowFrameChoices();
    }

    /**
     * Starts a new round refilling the {@link server.model.state.boards.draftpool.DraftPool} drawing new dices from the
     * {@link server.model.state.bag.Bag} and setting active the next player returned by the {@link RoundManager} iterator,
     * iterating while the next player will not be suspended. Then notifies the new turn and the new draft pool to the players.
     */
    void startRound() {
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
        while(active.isSuspended()){
            if(roundManager.hasNext())
                active = roundManager.next();
            else {
                endRound();
                return;
            }
        }
        active.setActive();
        notifyStartTurn(active);
    }

    /**
     * If the indicated player is the current active player, ends his turn. Then the next active player will be selected
     * by the RoundManager, iterating while the selected player is suspended. If the RoundManager is empty, ends the current Round.
     * @param player the player whose turn will be ended.
     */
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
            notifyGameManager("Everybody disconnected.");
            return;
        }
        for(Player player : state.getPlayers()) player.endRound();
        notifyRoundTrackUpdate(state.getRoundTrack().getRound()-1, state.getRoundTrack().getRoundSet(state.getRoundTrack().getRound()-1).toArray(new Cell[0]));
        if(state.getRoundTrack().getRound() > RoundTrack.MAX_ROUND)
            endGame();
        else startRound();
    }

    /**
     * Ends the game, calculates players' points and list them into a scoreboard sorted by scores. Notifies the end game and
     * the scoreboard to the players and the winner to the GameManager.
     */
    public synchronized void endGame() {
        if(state.isGameFinished())
            return;
        state.setGameFinished(true);
        if(started) {
            List<Player> scoreboard = new ArrayList<>();
            for (Player player : state.getPlayers()) {
                player.calculatePoints(state);
                scoreboard.add(player);
            }
            scoreboard.sort(new PointsComparator());
            notifyEndGame(scoreboard.toArray(new Player[0]));
            Player winner = getWinner(scoreboard);
            notifyGameManager("Winner: " + winner.getName());
        }
        //this.playerObserverMap = null;
    }

    private Player getWinner(List<Player> scoreboard){
        return scoreboard.stream()
                .filter( player -> !player.isSuspended())
                .findFirst()
                .orElse(scoreboard.get(0));
    }

    void notifyGameManager(String message){
        gameManager.endGame(this, message);
    }

    private synchronized void reinsertPlayer(Player player){
        player.setSuspended(false);
        Observer o = playerObserverMap.get(player);
        o.updateObjectiveCards(state.getPublicObjectiveCards().toArray(new PublicObjectiveCard[0]));
        o.updateToolCards(state.getToolCards().toArray(new ToolCard[0]));
        o.updateMutableData();
        o.updatePrivateObjectiveCard(player.getPrivateObjectiveCard());
        notifyReinsertPlayer(player);
    }


    /**
     * If the game is not started yet, removes the player from the player list, printing the current waiting players for this game,
     * otherwise suspends the player and notifies all the other players.
     * @param player
     */
    public synchronized void suspendPlayer(Player player){
        if(acceptPlayers) {
            removePlayer(player);
            player.setSuspended(true);
            System.out.print(this.hashCode() + "\n");
            state.getPlayers().stream().forEach(p -> System.out.print("\t" + p.getName() + "\n"));
            System.out.print(">>>");
        }
        else if(!player.isSuspended() && started && !state.isGameFinished()) {
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

    /**
     * Method declared for the {@link SinglePlayerModel} inheritance,for setting declaring how many tool cards he want to
     * use during his game. Does not do anything.
     * @param toolCards the number of tool cards chosen.
     */
    public void toolCardsChoice(int toolCards){
    }

    /**
     * Method declared for the {@link SinglePlayerModel} inheritance,for setting the private objective card selected by the player
     * for calculate his points. Does not do anything.
     * @param card the chosen private objective card index.
     */
    public void privateCardChoice(int card){

    }

    /**
     * Notifies the window frame chosen by the player. If all other players have chosen their window frame, the game starts.
     * @param player the player who performed the choice.
     * @param windowFrameList the chosen frame's rep.
     */
    public synchronized void windowFrameChoice(Player player, WindowFrameList windowFrameList){
        player.setWindowFrame(windowFrameList);
        player.setInactive();
        for(Player p : state.getPlayers()){
            if (p.getWindowFrame() == null) return;
        }
        if(!state.isGameFinished()) init();
    }

    /**
     * Moves a dice from the source {@link Cell} to the target, throwing {@link InvalidMoveException} if the move is not valid.
     * Notifies the move to the players.
     * @param player the player performing the move.
     * @param source the source cell.
     * @param target the target cell.
     * @throws InvalidMoveException if the move is not valid.
     */
    public void move(Player player, Cell source, Cell target) throws InvalidMoveException {
        source.move(target);
        notifyMove(player, source, target);
    }

    /**
     * Switches the two cell's dices throwing Invalid move exception if this switch is not valid. Notifies the switch as
     * a move and then a put.
     * @param player the player performing the switch.
     * @param first the first cell.
     * @param second the second cell.
     * @throws InvalidMoveException if the switch is not valid.
     */
    public void exchange(Player player, Cell first, Cell second) throws InvalidMoveException {
        Dice dice= second.removeDice();
        move(player, first, second);
        putDice(player, dice, first);
    }

    /**
     * Puts the {@link Dice} in the selected cell throwing exception if the cell is already filled. Then notifies it to the players.
     * @param player the player performing the move.
     * @param dice the dice to be placed.
     * @param target the cell in which the dice will be inserted.
     * @throws InvalidMoveException if the cell is already filled.
     */
    public void putDice(Player player, Dice dice, Cell target) throws InvalidMoveException {
        target.put(dice);
        notifyCellChangement(player, target);
    }

    /**
     * Increase the cell's dice value by 1 and notifies it to the players throwing exception if the increase is not valid.
     * @param player the player performing the increase.
     * @param cell the cell whose dice will be increased.
     * @throws InvalidMoveException if the cell is empty or the increase is not valid.
     */
    public void increase(Player player, Cell cell) throws InvalidMoveException {
        cell.getDice().increase();
        notifyCellChangement(player, cell);
    }

    /**
     * Decrease the cell's dice value by 1 and notifies it to the players throwing exception if the decrease is not valid.
     * @param player the player performing the decrease.
     * @param cell the cell whose dice will be decreased.
     * @throws InvalidMoveException if the cell is empty or the decrease is not valid.
     */
    public void decrease(Player player, Cell cell) throws InvalidMoveException {
        cell.getDice().decrease();
        notifyCellChangement(player, cell);
    }

    /**
     * Draws a new dice from the bag and notifies it to the the players.
     * @param player the player performing the draw.
     * @return the dice drawn.
     */
    public Dice drawDice(Player player) {
        Dice dice=state.getBag().draw();
        notifyDraw(player, dice);
        return dice;
    }

    /**
     * Flips the cell's dice.
     * @param player the player performing the move.
     * @param cell the cell whose dice will be flipped.
     */
    public void flipDice(Player player, Cell cell) {
        cell.getDice().flip();
        notifyCellChangement(player, cell);
    }

    /**
     * Removes the cell's dice if present, throws InvalidMoveException otherwise, and notifies this to the players
     * @param player the player performing the remove.
     * @param cell the cell whose dice will be removed.
     * @throws InvalidMoveException if the cell is empty.
     */
    public void remove(Player player, DraftPoolCell cell) throws InvalidMoveException {
        cell.removeDice();
        notifyRemovedDice(player, cell);
    }

    /**
     * Increase the tool card's cost, in case of first use, and reduces player's tokens by the card's cost. Notifies
     * this to the players.
     * @param player the player that used the tool card.
     * @param toolCard the tool card used.
     */
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

    /**
     * Notifies a dice move.
     * @param player the player that performed the move.
     * @param source the source cell.
     * @param target the target cell.
     */
    @Override
    public synchronized void notifyMove(Player player, Cell source, Cell target) {
        for(Observer o: playerObserverMap.values()) o.updateMove(player, source, target);
    }

    /**
     * Notifies a cell change.
     * @param player the player that performed the change.
     * @param cell the cell changed.
     */
    @Override
    public synchronized void notifyCellChangement(Player player, Cell cell) {
        for(Observer o: playerObserverMap.values()) o.updateCellChangement(player, cell);
    }

    /**
     * Notifies the new dices drawn and inserted into the draft pool.
     * @param draftPool the new draft pool dice's array.
     */
    @Override
    public synchronized void notifyRefillDraftPool(Cell[] draftPool) {
        for(Observer o: playerObserverMap.values()) o.updateRefillDraftPool(draftPool);
    }

    /**
     * Notifies the tool cards drawn for the game.
     */
    @Override
    public synchronized void notifyToolCards() {
        for(Observer o: playerObserverMap.values())
            o.updateToolCards(state.getToolCards().toArray(new ToolCard[0]));
    }

    /**
     * Notifies the public objective cards drawn for the game.
     */
    @Override
    public synchronized void notifyObjectiveCards() {
        for(Observer o: playerObserverMap.values())
            o.updateObjectiveCards(state.getPublicObjectiveCards().toArray(new PublicObjectiveCard[0]));
    }

    /**
     * Notifies for each player his window frame choices.
     */
    @Override
    public synchronized void notifyWindowFrameChoices() {
        for(Observer o: playerObserverMap.values()) {
            o.updateWindowFrameChoices(util.getWindowFrameChoice());
        }
    }

    /**
     * Notifies the players playing in this game.
     * @param players the array of playing players.
     */
    @Override
    public synchronized void notifyPlayers(Player[] players) {
        for(Observer o: playerObserverMap.values()) o.updatePlayers(players);
    }

    /**
     * Notifies a tool card effect use.
     * @param player the player that used the tool card's effect.
     * @param toolCard the tool card used.
     * @param tokens the tokens the player spent in order to use tool card's effect.
     */
    @Override
    public synchronized void notifyToolCardUsed(Player player, ToolCard toolCard, int tokens) {
        for(Observer o: playerObserverMap.values()) o.updateToolCardUsed(player, toolCard, tokens);
    }

    /**
     * Notifies a dice draw.
     * @param player the player that performed the draw.
     * @param dice the dice drawn.
     */
    @Override
    public synchronized void notifyDraw(Player player, Dice dice) {
        for(Observer o : playerObserverMap.values()) o.updateDiceDraw(player, dice.getColor());
    }

    /**
     * Notifies for each player his private objective card.
     */
    @Override
    public synchronized void notifyPrivateObjectiveCard() {
        for(Observer o: playerObserverMap.values()) {
            o.updatePrivateObjectiveCard(util.getCard());
        }
    }

    /**
     * Notifies the beginning of a new turn.
     * @param player the new active player.
     */
    @Override
    public synchronized void notifyStartTurn(Player player) {
        for(Observer o : playerObserverMap.values())
            o.updateStartTurn(player);
    }

    /**
     * Notifies the dice inserted into a round track set.
     * @param round the round track cell's round.
     * @param cells the array of cells inserted into the set.
     */
    @Override
    public synchronized void notifyRoundTrackUpdate(int round, Cell[] cells){
        for(Observer o : playerObserverMap.values()) o.updateRoundTrack(round, cells);
    }

    /**
     * Notifies the end of the current game and the ranking.
     * @param scoreboard the array of players sorted by the points scored.
     */
    @Override
    public synchronized void notifyEndGame(Player[] scoreboard){
        for (Observer o: playerObserverMap.values()) o.updateEndGame(scoreboard);
        for (Player p : state.getPlayers()) p.endGame();

    }

    /**
     * Notifies the player reconnection.
     * @param player the player reconnected.
     */
    @Override
    public synchronized void notifyReinsertPlayer(Player player) {
        for(Observer o : playerObserverMap.values())
            o.updateReinsertPlayer(player);
    }

    /**
     * Notifies the player disconnection.
     * @param player the disconnected player.
     */
    @Override
    public synchronized void notifySuspendPlayer(Player player){
        for(Observer o : playerObserverMap.values())
            o.updateSuspendPlayer(player);
    }

    /**
     * Notifies a dice removal.
     * @param player the player that performed the removal.
     * @param cell the cell whose dice has been removed.
     */
    @Override
    public synchronized void notifyRemovedDice(Player player, DraftPoolCell cell) {
        for(Observer o : playerObserverMap.values())
            o.updateRemovedDice(player, cell);
    }


}
