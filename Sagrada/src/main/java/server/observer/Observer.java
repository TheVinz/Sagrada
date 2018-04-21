package server.observer;

import server.state.player.Player;
import server.state.boards.Cell;
import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.toolcards.ToolCard;

public interface Observer {
    void updateMove(Cell source, Cell target);
    void updateCellChangement(Cell cell);
    void updateRefillDraftPool(Cell[] draftPool);
    void updateToolCards(ToolCard[] toolCards);
    void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards);
    void updateWindowFrameChoices(WindowFrame[] windowFrames);
    void updatePlayers(Player[] players);
    void updateToolCardUsed(Player player, ToolCard toolCard);
    void updatePrivateObjectiveCard(PrivateObjectiveCard card);
    void updateStartTurn();
}
