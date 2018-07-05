package server.observer;

public interface SinglePlayerObservable extends Observable {
    /**
     * Notifies the player he has to chose the difficulty.
     */
    void notifyToolCardsChoice();
}
