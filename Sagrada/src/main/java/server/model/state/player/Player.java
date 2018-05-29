package server.model.state.player;

import server.model.state.State;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.utilities.Timer;

public class Player {
    private String name;
    private int id;
    private WindowFrame windowFrame;
    private PrivateObjectiveCard privateObjectiveCard;
    private int favorTokens;
    private Points points;

    private boolean firstMoveDone;  // Aggiornato massimo una volta per partita

    private boolean secondTurn;     // Aggiornato massimo una volta per round

    private boolean diceMoved;      // |
    private boolean toolCardUsed;   // |-> Aggiornati massimo una volpta per turno
    private boolean active;         // |
    private boolean jumpSecondTurn;
    private boolean suspended;

    private Timer timer;


    public Player(String name, int id){
        this.name=name;
        this.id=id;
        this.diceMoved=false;
        this.toolCardUsed=false;
        this.secondTurn=false;
        this.firstMoveDone=false;
        this.jumpSecondTurn=false;
        this.points=new Points();
        this.suspended=false;
    }

    public void setTimer(Timer timer){
        this.timer=timer;
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
        if(!firstMoveDone) {
            System.out.println("first move");
            firstMoveDone=true;
        }
    }
    public void setToolCardUsed(){ toolCardUsed=true; }
    public void setActive(){
        this.active=true;
        timer.start();
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
    public int getFavorTokens() {
        return favorTokens;
    }
    public Timer getTimer(){return this.timer;}
    public PrivateObjectiveCard getPrivateObjectiveCard(){
        return this.privateObjectiveCard;
    }
    public int getPrivatePoints(){
        return privateObjectiveCard.calculatePoints(windowFrame);
    }

    public void endTurn() {
        this.diceMoved=false;
        this.toolCardUsed=false;
        this.active=false;
        this.secondTurn=true;
        timer.stop(); //forse non va
    }
    public void endRound() {
        secondTurn=false;
        jumpSecondTurn=false;
    }

    public void setInactive(){
        this.active =false;
        timer.stop();
    }

    public boolean isJumpSecondTurn() {
        return jumpSecondTurn;
    }

    public void setJumpSecondTurn(boolean jumpSecondTurn) {
        this.jumpSecondTurn = jumpSecondTurn;
    }

    public Points getPoints() {
        return points;
    }



    public void calculatePoints(State state){
        for (int card=0; card<3; card++) {
            points.setPointsFromPublicCard(card, state.getPublicObjectiveCards()[card].calculatePoints(windowFrame));
        }
        points.setPointsFromPrivateCard(privateObjectiveCard.calculatePoints(windowFrame));
        points.setPointsFromFavorTokens(favorTokens);
        points.setPointsFromEmptyCells(windowFrame.getEmptyCells());
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }
}
