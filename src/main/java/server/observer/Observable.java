package server.observer;

import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.boards.Cell;
import server.model.state.toolcards.ToolCard;

import java.rmi.RemoteException;

/**
 * Observable interface for the Observer pattern.
 */
public interface Observable {
    /**
     * Notifies a dice move.
     * @param player the player that performed the move.
     * @param source the source cell.
     * @param target the target cell.
     */
    void notifyMove(Player player, Cell source, Cell target) throws RemoteException;
    /**
     * Notifies a cell change.
     * @param player the player that performed the change.
     * @param cell the cell changed.
     */
    void notifyCellChangement(Player player, Cell cell) throws RemoteException;

    /**
     * Notifies the new dices drawn and inserted into the draft pool.
     * @param draftPool the new draft pool dice's array.
     */
    void notifyRefillDraftPool(Cell[] draftPool) throws RemoteException;
    /**
     * Notifies the tool cards drawn for the game.
     */
    void notifyToolCards() throws RemoteException;
    /**
     * Notifies the public objective cards drawn for the game.
     */
    void notifyObjectiveCards() throws RemoteException;
    /**
     * Notifies for each player his window frame choices.
     */
    void notifyWindowFrameChoices() throws RemoteException;
    /**
     * Notifies the players playing in this game.
     * @param players the array of playing players.
     */
    void notifyPlayers(Player[] players) throws RemoteException;
    /**
     * Notifies a tool card effect use.
     * @param player the player that used the tool card's effect.
     * @param toolCard the tool card used.
     * @param tokens the tokens the player spent in order to use tool card's effect.
     */
    void notifyToolCardUsed(Player player, ToolCard toolCard, int tokens) throws RemoteException;
    /**
     * Notifies a dice draw.
     * @param player the player that performed the draw.
     * @param dice the dice drawn.
     */
    void notifyDraw(Player player, Dice dice) throws RemoteException;
    /**
     * Notifies for each player his private objective card.
     */
    void notifyPrivateObjectiveCard() throws RemoteException;
    /**
     * Notifies the beginning of a new turn.
     * @param player the new active player.
     */
    void notifyStartTurn(Player player) throws RemoteException;
    /**
     * Notifies the dice inserted into a round track set.
     * @param round the round track cell's round.
     * @param cells the array of cells inserted into the set.
     */
    void notifyRoundTrackUpdate(int round, Cell[] cells);
    /**
     * Notifies the end of the current game and the ranking.
     * @param scoreboard the array of players sorted by the points scored.
     */
    void notifyEndGame(Player[] scoreboard);
    /**
     * Notifies the player reconnection.
     * @param player the player reconnected.
     */
    void notifyReinsertPlayer(Player player);
    /**
     * Notifies the player disconnection.
     * @param player the disconnected player.
     */
    void notifySuspendPlayer(Player player);
    /**
     * Notifies a dice removal.
     * @param player the player that performed the removal.
     * @param cell the cell whose dice has been removed.
     */
    void notifyRemovedDice(Player player, DraftPoolCell cell);
}
