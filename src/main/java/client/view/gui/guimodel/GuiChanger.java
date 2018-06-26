package client.view.gui.guimodel;

import common.Changer;
import client.view.gui.guicontroller.ViewController;
import common.viewchangement.*;
import javafx.application.Platform;

/**
 * The class <tt>GuiChanger</tt> translates the {@link Changement}s received from the server in method
 * calls for the {@link ViewController}, so it can update the current graphical representation of the game.
 * Each different change receives as parameter the {@link Changement} containing info about how the game
 * state changed and the player who performed this change.
 */
public class GuiChanger implements Changer {

    private ViewController view; //da fare la get
    private int id;

    /**
     * Initializes the <tt>GuiChanger</tt> setting the {@link ViewController} this GuiChanger should
     * inform about {@link Changement}s received from the server.
     * @param view the ViewController of the client.
     */
    public GuiChanger(ViewController view){
        this.view = view;
    }

    /**
     * Informs the ViewController about a dice move represented by the {@link Move} Changement.
     * @param move the Changement representing the move.
     */
    @Override
    public void change(Move move){
        if(move.getParam4()==-1){
            Platform.runLater(() -> view.move(move.getPlayerId(), move.getSourceType(), move.getTargetType(), move.getParam1(), move.getParam2(), move.getParam3()));
        }
        else{
            Platform.runLater(() -> view.move(move.getPlayerId(), move.getSourceType(), move.getTargetType(), move.getParam1(), move.getParam2(), move.getParam3(), move.getParam4()));
        }
    }

    /**
     * This method is called when the client reconnects to a game still in progress, so it set the whole
     * view game state from the {@link MutableData} Changement and then re-initializes the game window.
     * @param mutableData the Changement representing the game state.
     */
    @Override
    public void change(MutableData mutableData) {
        Platform.runLater(() -> {
            view.loadPlayers(mutableData.getNames(), mutableData.getIds(), mutableData.getWindowFrameReps(), mutableData.getFavorTokens(), mutableData.getId());
            for(int i=0; i<mutableData.getIds().length; i++) {
                view.setWindowFrameDices(i, mutableData.getWindowFrameValues()[i], mutableData.getWindowFrameColors()[i]);
            }
            for(int i=1; i<mutableData.getRoundTrackValues().length; i++) {
                view.updateRoundTrack(i,mutableData.getRoundTrackValues()[i], mutableData.getRoundTrackColors()[i]);
            }
            view.setDraftPool(mutableData.getDraftPoolValues(), mutableData.getDraftPoolColors());
            view.setGameBackground();
            view.startGame();
        });
    }


    /**
     * Informs the ViewController about dice re-draws and re-rolls, the {@link CellUpdate} Changement
     * contains the cell coordinates and dice value and color. This method is called only for window frame cell's
     * changes.
     * @param cellUpdate the Changement containing cell coordinates and dice value and color.
     */
    @Override
    public void change(CellUpdate cellUpdate){
        if(cellUpdate.getColumn()==-1){
            Platform.runLater(() -> view.updateCell(cellUpdate.getPlayerId(), cellUpdate.getCellType(), cellUpdate.getRow(), cellUpdate.getValue(), cellUpdate.getColor()));
        }
        else{
            //Platform.runLater(() -> view.updateCell(cellUpdate.getPlayerId(), cellUpdate.getCellType(), cellUpdate.getRow(), cellUpdate.getColumn(), cellUpdate.getValue(), cellUpdate.getColor()));
        }
    }


    /**
     * Load the tool cards drawn at the beginning of the game.
     * @param loadToolCards the Changement containing the tool cards identifiers.
     */
    @Override
    public void change(LoadToolCards loadToolCards) {
        Platform.runLater(() -> view.loadToolCard(loadToolCards.getToolCards()));
    }

    /**
     * Informs the ViewController about the dices drawn at the beginning of the new Round.
     * @param refilledDraftPool the Changement containing the dices colors and values.
     */
    @Override
    public void change(RefilledDraftPool refilledDraftPool) {
        Platform.runLater(() -> view.setDraftPool(refilledDraftPool.getValues(), refilledDraftPool.getColors()));
    }

    /**
     * Informs the ViewController about the public objective cards for drawn at the beginning of the game.
     * @param loadPublicObjectiveCards the Changement containing the informations about the cards.
     */
    @Override
    public void change(LoadPublicObjectiveCards loadPublicObjectiveCards) {
        Platform.runLater(() -> view.setPublicObjectiveCards(loadPublicObjectiveCards.getPublicObjectiveCards()));
    }

    /**
     * Loads the player's window frame sheme choice before the game starts.
     * @param windowFrameChoices the Changement containing the window frame scheme's.
     */
    @Override
    public void change(WindowFrameChoices windowFrameChoices) {
        Platform.runLater(() -> view.loadWindowFrameChoice(windowFrameChoices.getReps(), windowFrameChoices.getFavorTokens()));
    }

    /**
     * Informs the ViewController about the other players playing the game, also including the id the server
     * associated to the local client. In case of singleplayer games, this Changement only contains
     * informations about the single player.
     * @param loadPlayers the Changement containing informations about the players.
     */
    @Override
    public void change(LoadPlayers loadPlayers) {
        Platform.runLater(() -> {
            view.loadPlayers(loadPlayers.getNames(), loadPlayers.getIds(), loadPlayers.getWindowFrameReps(), loadPlayers.getWindowFrameFavorTokens(), id);
            view.startGame();
        });
    }

    /**
     * Informs the ViewController about a tool card's use by a player.
     * @param toolCardUsed the Changement containing the coordinates to identify the tool card and the player.
     */
    @Override
    public void change(ToolCardUsed toolCardUsed) {
        Platform.runLater(() -> view.toolCardUsed(toolCardUsed.getId(), toolCardUsed.getIndex(), toolCardUsed.getTokens()));

    }

    /**
     * Load the private objective cards drawn at the beginning of the game. This Changement may contains
     * one single card in case of multi-player game or two cards for single-player.
     * @param loadPrivateObjectiveCard the Changement containing informations about the private objective cards.
     */
    @Override
    public void change(LoadPrivateObjectiveCard loadPrivateObjectiveCard) {
        Platform.runLater(() -> view.loadPrivateObjectiveCard(loadPrivateObjectiveCard.getColor()));
    }

    /**
     * Notifies to the ViewController the beginning of a new turn and the new active player.
     * @param newTurn the Changement containing informations about the active player.
     */
    @Override
    public void change(NewTurn newTurn) {
        Platform.runLater(() -> view.startTurn(newTurn.getId()));
    }

    /**
     * Method called after a new dice draw thanks to the effect of the card "pennello per pasta salda".
     * @param diceDraw the Changement containing informations about the dice drawn.
     */
    @Override
    public void change(DiceDraw diceDraw) {
        Platform.runLater(() -> view.notifyDiceDraw(diceDraw.getId(), diceDraw.getColor()));
    }

    /**
     * Informs the ViewController about the dices to be added to the round track.
     * @param loadLastRoundTrack the Changement containing informations about dices and round track cell
     *                           where those dices should be putted.
     */
    @Override
    public void change(LoadLastRoundTrack loadLastRoundTrack) {
        Platform.runLater(() -> view.updateRoundTrack(loadLastRoundTrack.getRound(), loadLastRoundTrack.getValues(), loadLastRoundTrack.getColors()));
    }


    @Override @Deprecated
    public void change(LoadId loadId){
        this.id=loadId.getId();
    }

    /**
     * Informs the ViewController about the end of the a multi-player game.
     * @param endGame the Changement containing informations about the points scored by each player.
     */
    @Override
    public void change(EndGame endGame) {
        Platform.runLater(() -> view.endGame(endGame.getCharCards(), endGame.getScoreboardIds(), endGame.getMatrixPoins()));
    }

    /**
     * Informs the ViewController about the end of a single-player game.
     * @param singlePlayerEndGame the Changement containing informations about the points scored by the player
     *                            and the target points to reach for the victory.
     */
    @Override
    public void change(SinglePlayerEndGame singlePlayerEndGame) {
        Platform.runLater(() -> view.endSinglePlayerGame(singlePlayerEndGame.getCard(), singlePlayerEndGame.getVectorPoints(), singlePlayerEndGame.getTargetPoints()));
    }

    /**
     * Informs the ViewController about a reconnection.
     * @param reinsertedPlayer the Changement containing informations about the player reconnected.
     */
    @Override
    public void change(ReinsertedPlayer reinsertedPlayer) {
        Platform.runLater(() -> view.notifyPlayerReconnected(reinsertedPlayer.getIdPlayer()));
    }

    /**
     * Informs the ViewController about a dice removed from the draft pool.
     * @param removedDice the Changement containing the cell coordinates.
     */
    @Override
    public void change(RemovedDice removedDice) {
        Platform.runLater(() -> view.removeDraftPoolDice(removedDice.getIndex()));
    }

    /**
     * Informs the ViewController when a player disconnect from the game.
     * @param suspendedPlayer the Changement containing the disconnected player's id.
     */
    @Override
    public void change(SuspendedPlayer suspendedPlayer) {
        Platform.runLater(() -> view.notifyPlayerDisconnected(suspendedPlayer.getPlayerId()));
    }

    /**
     * Informs the ViewController that the single-player game is starting, so the player should
     * choose how many tool cards does he want to use during this game.
     * @param toolCardsChoices this Changement does not contain any information, is just used to
     *                         distinguish this event from the others.
     */
    @Override
    public void change(ToolCardsChoices toolCardsChoices) {
        Platform.runLater(() -> view.choseDifficulty());
    }

    /**
     * Informs the ViewController that the single-player game ended and it's time for the player to
     * choose which private objective card will be used to calculate his points.
     * @param privateObjectiveCardsChoice the Changement containing the private objective cards
     *                                    the player can choose.
     */
    @Override
    public void change(PrivateObjectiveCardsChoice privateObjectiveCardsChoice) {
        char card1 = privateObjectiveCardsChoice.getCard1();
        char card2 = privateObjectiveCardsChoice.getCard2();
        Platform.runLater(() -> view.notifyPrivateObjectiveCardChoice(card1, card2));
    }
}
