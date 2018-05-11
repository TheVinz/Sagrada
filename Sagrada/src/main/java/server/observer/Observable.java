package server.observer;

import server.state.dice.Dice;
import server.state.player.Player;
import server.state.boards.Cell;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.toolcards.ToolCard;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyMove(Player player, Cell source, Cell target);
    void notifyCellChangement(Player player, Cell cell);
    void notifyRefillDraftPool(Cell[] draftPool);
    void notifyToolCards();
    void notifyObjectiveCards(PublicObjectiveCard[] publicObjectiveCards);
    void notifyWindowFrameChoices();
    void notifyPlayers(Player[] players);
    void notifyToolCardUsed(Player player, ToolCard toolCard);
    void notifyDraw(Player player, Dice dice);
    void notifyPrivateObjectiveCard();
    void notifyStartTurn(Observer o);
}
