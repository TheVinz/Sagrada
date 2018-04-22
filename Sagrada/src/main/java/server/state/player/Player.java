package server.state.player;

import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;

public class Player {
    private String name;
    private int id;
    private WindowFrame windowFrame;
    private PrivateObjectiveCard privateObjectiveCard;
    private boolean firstMoveDone;

    public Player(String name, int id){
        this.name=name;
        this.id=id;
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
}
