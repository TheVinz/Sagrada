package server.observer;

import common.Notification;
import common.response.Response;
import common.viewchangement.*;
import server.controller.Controller;
import server.model.state.boards.draftpool.DraftPoolCell;
import server.model.state.player.Player;
import server.model.state.boards.Cell;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Points;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Color;


public interface Observer {
    /**
     * Creates the <tt>Changement</tt> {@link Move} and calls the method send of the instance of this class with this changement as a parameter.
     * @param player the player who made the move.
     * @param source the source cell of the move.
     * @param target the target cell of the move.
     */
    void updateMove(Player player, Cell source, Cell target);
    /**
     * Creates the <tt>Changement</tt> {@link CellUpdate} and calls the method send of the instance of this class with this changement as a parameter.
     * @param player the player who made the CellUpdate.
     * @param cell the Cell to update.
     */
    void updateCellChangement(Player player, Cell cell);
    /**
     * Creates the <tt>Changement</tt> {@link RefilledDraftPool} and calls the method send of the instance of this class with this changement as a parameter.
     * @param draftPool the new {@link server.model.state.boards.draftpool.DraftPool}.
     */
    void updateRefillDraftPool(Cell[] draftPool);
    /**
     * Creates the <tt>Changement</tt> {@link LoadToolCards} and calls the method send of the instance of this class with this changement as a parameter.
     * @param toolCards the {@link ToolCard}s to put in the game.
     */
    void updateToolCards(ToolCard[] toolCards);
    /**
     * Creates the <tt>Changement</tt> {@link LoadPublicObjectiveCards} and calls the method send of the instance of this class with this changement as a parameter.
     * @param publicObjectiveCards the {@link PublicObjectiveCard}s to put in the game.
     */
    void updateObjectiveCards(PublicObjectiveCard[] publicObjectiveCards);
    /**
     * Creates the <tt>Changement</tt> {@link WindowFrameChoices} and calls the method send of the instance of this class with this changement as a parameter.
     * @param windowFrameLists the list of {@link server.model.state.boards.windowframe.WindowFrame} available at the beginning of the game.
     */
    void updateWindowFrameChoices(WindowFrameList[] windowFrameLists);
    /**Creates the <tt>Changement</tt> {@link LoadPlayers} and calls the method send of the instance of this class with this changement as a parameter.
     * @param players the players of a game.
     */
    void updatePlayers(Player[] players);
    /**
     * Creates the <tt>Changement</tt> {@link ToolCardUsed} and calls the method send of the instance of this class with this changement as a parameter.
     * @param player the player who used the ToolCard.
     * @param toolCard the ToolCard used.
     * @param tokens the FavorTokens on the ToolCard.
     */
    void updateToolCardUsed(Player player, ToolCard toolCard, int tokens);
    /**
     * Creates the <tt>Changement</tt> {@link LoadPrivateObjectiveCard} and calls the method send of the instance of this class with this changement as a parameter.
     * @param card the {@link PrivateObjectiveCard} to set to a player.
     */
    void updatePrivateObjectiveCard(PrivateObjectiveCard card);
    /**
     * Creates the <tt>Changement</tt> {@link NewTurn} and calls the method send of the instance of this class with this changement as a parameter.
     * @param player the player who's gonna play.
     */
    void updateStartTurn(Player player);
    /**
     * Creates the <tt>Changement</tt> {@link DiceDraw} and calls the method send of the instance of this class with this changement as a parameter.
     * @param player the player who drafted the Dice.
     * @param color the color of the drafted Dice.
     */
    void updateDiceDraw(Player player, Color color);
    /**
     * Creates the <tt>Changement</tt> {@link LoadLastRoundTrack} and calls the method send of the instance of this class with this changement as a parameter.
     * @param round the round just passed.
     * @param cells the Dice to put in the {@link server.model.state.boards.roundtrack.RoundTrack} at the given round.
     */
    void updateRoundTrack(int round, Cell[] cells);
    /**
     * Creates the <tt>Changement</tt> {@link EndGame} and calls the method send of the instance of this class with this changement as a parameter.
     * @param scoreboard the final scoreboard, an array of ordered id.
     */
    void updateEndGame(Player[] scoreboard);
    /**
     * Creates the <tt>Changement</tt> {@link MutableData} and calls the method send of the instance of this class with this changement as a parameter.
     */
    void updateMutableData();
    /**
     * Creates the <tt>Changement</tt> {@link ReinsertedPlayer} and calls the method send of the instance of this class with this changement as a parameter.
     * @param player the player who's gonna be reinserted into the game.
     */
    void updateReinsertPlayer(Player player);
    /**
     * Creates the <tt>Changement</tt> {@link SuspendedPlayer} and calls the method send of the instance of this class with this changement as a parameter.
     * @param player the player who's gonna be suspended.
     */
    void updateSuspendPlayer(Player player);
    /**
     * Creates the <tt>Changement</tt> {@link ToolCardsChoices} and calls the method send of the instance of this class with this changement as a parameter.
     */
    void updateToolCardsChoice();
    /**
     * Creates the <tt>Changement</tt> {@link RemovedDice} and calls the method send of the instance of this class with this changement as a parameter.
     * @param player the player who removed the Dice.
     * @param cell the Cell where has been removed the Dice.
     */
    void updateRemovedDice(Player player, DraftPoolCell cell);

    /**
     * Calls the send method of the instance of this class with the next parameter requested to the client and elaborated by the {@link Controller}.
     * @param response the next parameter.
     */
    void notifyNextParameter(Response response);
    /**
     * Creates a new {@link Notification} and calls the method notify of the instance of this class with it as parameter.
     * @param message the message error elaborated by the Controller to add to the Notification.
     */
    void notifyError(String message);
    /**
     * Creates a new {@link Notification} and calls the method notify of the instance of this class with it as parameter.
     * @param message the message of wrong parameter elaborated by the Controller to add to the Notification.
     */
    void notifyWrongParameter(String message);
    /**
     * Creates the <tt>Changement</tt> {@link PrivateObjectiveCardsChoice} and calls the method send of the instance of this class with this changement as a parameter.
     */
    void updatePrivateObjectiveCardChoice();
    /**
     * Creates the <tt>Changement</tt> {@link SinglePlayerEndGame} and calls the method send of the instance of this class with this changement as a parameter.
     * @param targetPoints the score to beat in SinglePlayer mode.
     * @param points the points achieved by the player.
     * @param privateObjectiveCard the {@link Color} of the PrivateObjectiveCard chose by the player.
     */
    void updateSinglePlayerEndGame(int targetPoints, Points points, PrivateObjectiveCard privateObjectiveCard);
}
