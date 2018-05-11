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
    private boolean active;

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

    public void setDiceMoved(){
        this.diceMoved=true;
    }

<<<<<<< HEAD
    public int getId() {
        return id;
    }

    public void setDiceMoved(boolean diceMoved) {
        this.diceMoved = diceMoved;
=======
    public boolean isActive() {
        return this.active;
    }
    public boolean isDiceMoved(){
        return this.diceMoved;
    }
    public boolean isToolCardUsed(){
        return this.toolCardUsed;
>>>>>>> 1fec6cf48718e7abd567f081ad211c86594442c1
    }
}
