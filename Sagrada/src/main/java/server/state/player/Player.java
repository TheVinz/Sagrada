package server.state.player;

import server.Model;
import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;

public class Player {
    private String name;
    private int id;
    private WindowFrame windowFrame;
    private PrivateObjectiveCard privateObjectiveCard;
    private boolean firstMoveDone;
    private boolean activePlayer;

    private boolean diceMoved;
    private boolean toolCardUsed;

    public Player(String name, int id, Model model){
        this.name=name;
        this.id=id;
        this.diceMoved=false;
        this.toolCardUsed=false;
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


    public int getId() {
        return id;
    }

    public void setDiceMoved(boolean diceMoved) {
        this.diceMoved = diceMoved;
    }
}
