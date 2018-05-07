package server.state.player;

import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;

public class Player {
    private String name;
    private int id;
    private WindowFrame windowFrame;
    private PrivateObjectiveCard privateObjectiveCard;
    private boolean firstMoveDone; //da togliere

    private PlayerState diceMovedState;
    private PlayerState endingState;
    private PlayerState startingState;
    private PlayerState toolCardUsedState;
    private PlayerState usingToolCard;
    private PlayerState currentState;

    public Player(String name, int id, Model model){
        this.name=name;
        this.id=id;
        this.diceMovedState = new DiceMovedState();
        this.endingState = new EndingState();
        this.startingState = new StartingState(model, this);
        this.toolCardUsedState = new ToolCardUsedState();
        this.usingToolCard = new UsingToolCard();
        this.currentState = startingState;

    }

    public void setCurrentState(PlayerState currentState) {
        this.currentState = currentState;
    }

    public void setWindowFrame(WindowFrame windowFrame){
        this.windowFrame=windowFrame;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    public boolean isFirstMoveDone() {
        return firstMoveDone;
    }

    public void setFirstMoveDone(boolean firstMoveDone) {
        this.firstMoveDone = firstMoveDone;
    }

    public PlayerState getDiceMovedState() {
        return diceMovedState;
    }

    public PlayerState getEndingState() {
        return endingState;
    }

    public PlayerState getStartingState() {
        return startingState;
    }

    public PlayerState getToolCardUsedState() {
        return toolCardUsedState;
    }

    public PlayerState getUsingToolCard() {
        return usingToolCard;
    }


}
