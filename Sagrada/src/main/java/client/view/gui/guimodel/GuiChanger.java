package client.view.gui.guimodel;

import common.Changer;
import client.view.gui.guicontroller.ViewController;
import common.viewchangement.*;
import javafx.application.Platform;

public class GuiChanger implements Changer {

    private ViewController view; //da fare la get
    private int id;

    public GuiChanger(ViewController view){
        this.view = view;
    }

    @Override
    public void change(Move move){
        if(move.getParam4()==-1){
            Platform.runLater(() -> view.move(move.getPlayerId(), move.getSourceType(), move.getTargetType(), move.getParam1(), move.getParam2(), move.getParam3()));
        }
        else{
            Platform.runLater(() -> view.move(move.getPlayerId(), move.getSourceType(), move.getTargetType(), move.getParam1(), move.getParam2(), move.getParam3(), move.getParam4()));
        }
    }

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


    @Override
    public void change(CellUpdate cellUpdate){
        if(cellUpdate.getColumn()==-1){
            Platform.runLater(() -> view.updateCell(cellUpdate.getPlayerId(), cellUpdate.getCellType(), cellUpdate.getRow(), cellUpdate.getValue(), cellUpdate.getColor()));
        }
        else{
            Platform.runLater(() -> view.updateCell(cellUpdate.getPlayerId(), cellUpdate.getCellType(), cellUpdate.getRow(), cellUpdate.getColumn(), cellUpdate.getValue(), cellUpdate.getColor()));
        }
    }


    @Override
    public void change(LoadToolCards loadToolCards) {
        Platform.runLater(() -> view.loadToolCard(loadToolCards.getToolCards()));
    }

    @Override
    public void change(RefilledDraftPool refilledDraftPool) {
        Platform.runLater(() -> view.setDraftPool(refilledDraftPool.getValues(), refilledDraftPool.getColors()));
    }

    @Override
    public void change(LoadPublicObjectiveCards loadPublicObjectiveCards) {
        Platform.runLater(() -> view.setPublicObjectiveCards(loadPublicObjectiveCards.getPublicObjectiveCards()));
    }

    @Override
    public void change(WindowFrameChoices windowFrameChoices) {
        Platform.runLater(() -> view.loadWindowFrameChoice(windowFrameChoices.getReps(), windowFrameChoices.getFavorTokens()));
    }

    @Override
    public void change(LoadPlayers loadPlayers) {
        Platform.runLater(() -> {
            view.loadPlayers(loadPlayers.getNames(), loadPlayers.getIds(), loadPlayers.getWindowFrameReps(), loadPlayers.getWindowFrameFavorTokens(), id);
            view.startGame();
        });
    }

    @Override
    public void change(ToolCardUsed toolCardUsed) {
        Platform.runLater(() -> view.toolCardUsed(toolCardUsed.getId(), toolCardUsed.getIndex(), toolCardUsed.getTokens()));

    }

    @Override
    public void change(LoadPrivateObjectiveCard loadPrivateObjectiveCard) {
        Platform.runLater(() -> view.loadPrivateObjectiveCard(loadPrivateObjectiveCard.getColor()));
    }

    @Override
    public void change(NewTurn newTurn) {
        Platform.runLater(() -> view.startTurn(newTurn.getId()));
    }

    @Override
    public void change(DiceDraw diceDraw) {
        Platform.runLater(() -> view.notifyDiceDraw(diceDraw.getId(), diceDraw.getColor()));
    }

    @Override
    public void change(LoadLastRoundTrack loadLastRoundTrack) {
        Platform.runLater(() -> view.updateRoundTrack(loadLastRoundTrack.getRound(), loadLastRoundTrack.getValues(), loadLastRoundTrack.getColors()));
    }

    @Override
    public void change(LoadId loadId){
        this.id=loadId.getId();
    }


    @Override
    public void change(EndGame endGame) {
        Platform.runLater(() -> view.endGame(endGame.getCharCards(), endGame.getScoreboardIds(), endGame.getMatrixPoins()));
    }

    @Override
    public void change(SinglePlayerEndGame singlePlayerEndGame) {
        Platform.runLater(() -> view.endSinglePlayerGame(singlePlayerEndGame.getCard(), singlePlayerEndGame.getVectorPoints(), singlePlayerEndGame.getTargetPoints()));
    }

    @Override
    public void change(ReinsertedPlayer reinsertedPlayer) {
        Platform.runLater(() -> view.notifyPlayerReconnected(reinsertedPlayer.getIdPlayer()));
    }

    @Override
    public void change(RemovedDice removedDice) {
        Platform.runLater(() -> view.removeDraftPoolDice(removedDice.getIndex()));
    }

    @Override
    public void change(SuspendedPlayer suspendedPlayer) {
        Platform.runLater(() -> view.notifyPlayerDisconnected(suspendedPlayer.getPlayerId()));
    }

    @Override
    public void change(ToolCardsChoices toolCardsChoices) {
        Platform.runLater(() -> view.choseDifficulty());
    }

    @Override
    public void change(PrivateObjectiveCardsChoice privateObjectiveCardsChoice) {
        char card1 = privateObjectiveCardsChoice.getCard1();
        char card2 = privateObjectiveCardsChoice.getCard2();
        Platform.runLater(() -> view.notifyPrivateObjectiveCardChoice(card1, card2));
    }
}
