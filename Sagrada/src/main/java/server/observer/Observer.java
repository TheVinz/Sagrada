package server.observer;

import common.response.Response;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.player.Player;
import server.model.state.boards.Cell;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;


public interface Observer {
    void updateMove(Player player, Cell source, Cell target);
    void updateCellChangement(Player player, Cell cell);
    void updateRefillDraftPool(Cell[] draftPool);
    void updateToolCards(ToolCard[] toolCards);
    void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards);
    void updateWindowFrameChoices(WindowFrameList[] windowFrameLists);
    void updatePlayers(Player[] players);
    void updateToolCardUsed(Player player, ToolCard toolCard, int tokens);
    void updatePrivateObjectiveCard(PrivateObjectiveCard card);
    void updateStartTurn(Player player);
    void updateDiceDraw(Player player, Color color);
    void updateRoundTrack(int round, Cell[] cells);
    void updateEndGame(Player[] scoreboard);
    void updateMutableData();
    void updateReinsertPlayer(Player player);
    void updateSuspendPlayer(Player player);
    void updateToolCardsChoice();
    void updateRemovedDice(Player player, DraftPoolCell cell);
    void notifyNextParameter(Response response);
    void notifyError(String message);
    void notifyWrongParameter(String message);
}
