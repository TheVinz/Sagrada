package server.observer;

import server.model.state.dice.Dice;
import server.model.state.player.Player;
import server.model.state.boards.Cell;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.toolcards.ToolCard;

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
    void notifyStartTurn(Player player);
}
