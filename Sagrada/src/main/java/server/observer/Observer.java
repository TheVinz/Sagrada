package server.observer;

import server.state.dice.Dice;
import server.state.player.Player;
import server.state.boards.Cell;
import server.state.boards.windowframe.WindowFrameList;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.toolcards.ToolCard;
import server.state.utilities.Color;

public interface Observer {
    void updateMove(Cell source, Cell target);
    void updateCellChangement(Cell cell);
    void updateRefillDraftPool(Cell[] draftPool);
    void updateToolCards(ToolCard[] toolCards);
    void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards);
    void updateWindowFrameChoices(WindowFrameList[] windowFrameLists);
    void updatePlayers(Player[] players);
    void updateToolCardUsed(Player player, ToolCard toolCard);
    void updatePrivateObjectiveCard(PrivateObjectiveCard card);
    void updateStartTurn();
    void updateDiceDrow(Player player, Color color);
}
