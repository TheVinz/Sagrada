package common;


import common.viewchangement.*;

/**
 * The <tt>Changer</tt> interface defines the methods that translates each {@link Changement} to an action to be performed on a GUI or a CLI client.
 */
public interface Changer {
    /**
     * Applies the changes described in the {@link CellUpdate}.
     * @param cellUpdate the Changement sent by the server.
     */
    void change(CellUpdate cellUpdate);

    /**
     * Applies the changes described in the {@link DiceDraw}.
     * @param diceDraw the Changement sent by the server.
     */
    void change(DiceDraw diceDraw);

    /**
     * Notifies the end of a multi-game, the scoreboard is described into {@link EndGame}.
     * @param endGame the Changement sent by the server.
     */
    void change(EndGame endGame);

    /**
     * Saves the server identifier of the player {@link LoadId}.
     * @param loadId the Changement with the player's identifier.
     */
    void change(LoadId loadId);
    /**
     * Applies the changes described in the {@link LoadLastRoundTrack}.
     * @param loadLastRoundTrack the Changement sent by the server.
     */
    void change(LoadLastRoundTrack loadLastRoundTrack);
    /**
     * Saves the players of the joined game described inside the {@link LoadPlayers}.
     * @param loadPlayers the Changement with the players' data.
     */
    void change(LoadPlayers loadPlayers);
    /**
     * Saves the {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard}s drawn at the beginning of the game, described into the {@link LoadPrivateObjectiveCard}.
     * @param loadPrivateObjectiveCard the Changement sent by the server.
     */
    void change(LoadPrivateObjectiveCard loadPrivateObjectiveCard);
    /**
     * Saves the {@link server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard}s drawn at the beginning of the game, described into the {@link LoadPublicObjectiveCards}.
     * @param loadPublicObjectiveCards the Changement sent by the server.
     */
    /**
     * Saves the {@link server.model.state.toolcards.ToolCard}s drawn at the beginning of the game, described into the {@link LoadToolCards}.
     * @param loadToolCards the Changement sent by the server.
     */
    void change(LoadToolCards loadToolCards);
    /**
     * Applies the changes described in the {@link Move}.
     * @param move the Changement sent by the server.
     */
    void change(Move move);
    /**
     * Saves all the {@link server.model.state.State}'s data, described into the {@link MutableData}.
     * @param mutableData the Changement sent by the server.
     */
    void change(MutableData mutableData);
    /**
     * Starts a new turn, the current active player is indicated inside the {@link NewTurn}.
     * @param newTurn the Changement sent by the server.
     */
    void change(NewTurn newTurn);
    /**
     * Applies the changes described in the {@link RefilledDraftPool}.
     * @param refilledDraftPool the Changement sent by the server.
     */
    void change(RefilledDraftPool refilledDraftPool);
    /**
     * Notifies the client about the reconnection of the player indicated inside the {@link ReinsertedPlayer}.
     * @param reinsertedPlayer the Changement sent by the server.
     */
    void change(ReinsertedPlayer reinsertedPlayer);
    /**
     * Applies the changes described in the {@link RemovedDice}.
     * @param removedDice the Changement sent by the server.
     */
    void change(RemovedDice removedDice);
    /**
     * Notifies the client about the disconnection of the player indicated inside the {@link ReinsertedPlayer}.
     * @param suspendedPlayer the Changement sent by the server.
     */
    void change(SuspendedPlayer suspendedPlayer);
    /**
     * Require the player to choice the difficulty of the game.
     * @param toolCardsChoices the Changement sent by the server.
     */
    void change(ToolCardsChoices toolCardsChoices);
    /**
     * Notifies the player that someone used a ToolCard, the ToolCard used and the player are described inside the {@link ToolCardUsed}.
     * @param toolCardUsed the Changement sent by the server.
     */
    void change(ToolCardUsed toolCardUsed);
    /**
     * Notifies the player to choose the {@link server.model.state.boards.windowframe.WindowFrame}'s scheme to use during the game, the available choices are described inside the {@link client.view.cli.cliphasestate.WindowFrameChoice}.
     * @param windowFrameChoices the Changement sent by the server.
     */
    void change(WindowFrameChoices windowFrameChoices);
    /**
     * Notifies the player to choose the private objective card to use for calculate his points, the available cards are indicated inside the {@link PrivateObjectiveCardsChoice}.
     * @param privateObjectiveCardsChoice the Changement sent by the server.
     */
    void change(PrivateObjectiveCardsChoice privateObjectiveCardsChoice);
    /**
     * Notifies the player about the end of the single-player game, the player scores and the target points are described into {@link CellUpdate}.
     * @param singlePlayerEndGame the Changement sent by the server.
     */
    void change(SinglePlayerEndGame singlePlayerEndGame);
}
