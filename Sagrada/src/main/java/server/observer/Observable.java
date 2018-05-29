package server.observer;

import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.boards.Cell;
import server.model.state.toolcards.ToolCard;

import java.rmi.RemoteException;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyMove(Player player, Cell source, Cell target) throws RemoteException;
    void notifyCellChangement(Player player, Cell cell) throws RemoteException;
    void notifyRefillDraftPool(Cell[] draftPool) throws RemoteException;
    void notifyToolCards() throws RemoteException;
    void notifyObjectiveCards() throws RemoteException;
    void notifyWindowFrameChoices() throws RemoteException;
    void notifyPlayers(Player[] players) throws RemoteException;
    void notifyToolCardUsed(Player player, ToolCard toolCard, int tokens) throws RemoteException;
    void notifyDraw(Player player, Dice dice) throws RemoteException;
    void notifyPrivateObjectiveCard() throws RemoteException;
    void notifyStartTurn(Player player) throws RemoteException;
    void notifyRoundTrackUpdate(int round, Cell[] cells);
    void notifyEndGame(Player[] scoreboard);

    void notifyReinsertPlayer(Player player);

    void notifySuspendPlayer(Player player);
}
