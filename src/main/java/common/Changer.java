package common;


import common.viewchangement.*;


public interface Changer {
    void change(CellUpdate cellUpdate);
    void change(DiceDraw diceDraw);
    void change(EndGame endGame);
    void change(LoadId loadId);
    void change(LoadLastRoundTrack loadLastRoundTrack);
    void change(LoadPlayers loadPlayers);
    void change(LoadPrivateObjectiveCard loadPrivateObjectiveCard);
    void change(LoadPublicObjectiveCards loadPublicObjectiveCards);
    void change(LoadToolCards loadToolCards);
    void change(Move move);
    void change(MutableData mutableData);
    void change(NewTurn newTurn);
    void change(RefilledDraftPool refilledDraftPool);
    void change(ReinsertedPlayer reinsertedPlayer);
    void change(RemovedDice removedDice);
    void change(SuspendedPlayer suspendedPlayer);
    void change(ToolCardsChoices toolCardsChoices);
    void change(ToolCardUsed toolCardUsed);
    void change(WindowFrameChoices windowFrameChoices);
    void change(PrivateObjectiveCardsChoice privateObjectiveCardsChoice);
    void change(SinglePlayerEndGame singlePlayerEndGame);
}
