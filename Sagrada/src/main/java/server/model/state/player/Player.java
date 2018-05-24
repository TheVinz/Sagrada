package server.model.state.player;

import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;

public class Player {
    private String name;
    private int id;
    private WindowFrame windowFrame;
    private PrivateObjectiveCard privateObjectiveCard;
    private int favorTokens;

    private boolean firstMoveDone;  // Aggiornato massimo una volta per partita

    private boolean secondTurn;     // Aggiornato massimo una volta per round

    private boolean diceMoved;      // |
    private boolean toolCardUsed;   // |-> Aggiornati massimo una volpta per turno
    private boolean active;         // |


    public Player(String name, int id){
        this.name=name;
        this.id=id;
        this.diceMoved=false;
        this.toolCardUsed=false;
        this.secondTurn=false;
        this.firstMoveDone=false;
    }

    public void setWindowFrame(WindowFrameList windowFrameList){
        this.windowFrame=new WindowFrame(windowFrameList);
        this.favorTokens=windowFrame.getFavorToken();
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    public void removeFaforTokens(int tokens) {
        favorTokens=favorTokens-tokens;
    }

    public void setDiceMoved(){
        this.diceMoved=true;
        if(!firstMoveDone) firstMoveDone=true;
    }
    public void setToolCardUsed(){ toolCardUsed=true; }
    public void setActive(){
        this.active=true;
    }

    public boolean isFirstMoveDone() {
        return firstMoveDone;
    }
    public boolean isActive() {
        return this.active;
    }
    public boolean isDiceMoved(){
        return this.diceMoved;
    }
    public boolean isToolCardUsed(){
        return this.toolCardUsed;
    }
    public boolean isSecondTurn() {return this.secondTurn;}

    public String getName(){
        return this.name;
    }
    public int getId(){
        return this.id;
    }

    public void endTurn() {
        this.diceMoved=false;
        this.toolCardUsed=false;
        this.active=false;
        this.secondTurn=true;
    }
    public void endRound() {
        secondTurn=false;
    }

    public void setInactive(){
        this.active =false;
    }

}
