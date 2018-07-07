package server.model.state.player;

import server.model.state.State;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.boards.windowframe.WindowFrameList;
import server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard;
import server.model.state.utilities.Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * The <tt>Player</tt> class represents the state of a remote player int the game.
 */
public class Player {
    private String name;
    private int id;
    private WindowFrame windowFrame;
    private List<PrivateObjectiveCard> privateObjectiveCards;
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


    /**
     * Initializes a new <tt>Player</tt> with given username and game identifier.
     * @param name the player's username.
     * @param id the player identifier in the joined game.
     */
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
        this.privateObjectiveCards = new ArrayList<>();
    }

    /**
     * Sets the timer associated with this <tt>Player</tt>.
     * @param timer the player's timer.
     */
    public void setTimer(Timer timer){
        this.timer=timer;
    }

    /**
     * Creates this player's window frame from the {@link WindowFrameList} given and initializes the favor tokens.
     * @param windowFrameList the representation of the window frame the player chose.
     */
    public void setWindowFrame(WindowFrameList windowFrameList){
        this.windowFrame=new WindowFrame(windowFrameList);
        this.favorTokens=windowFrame.getFavorToken();
    }

    /**
     * Adds a {@link PrivateObjectiveCard} to the private card's list associated with the player.
     * @param privateObjectiveCard the private objective card drawn for the player.
     */
    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCards.add(privateObjectiveCard);
    }

    /**
     * Returns this player's {@link WindowFrame}.
     * @return returns the player's WindowFrame.
     */
    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    /**
     * Removes the amount of tokens indicated from this player's tokens.
     * @param tokens the amount of tokens the player spent.
     */
    public void removeFavorTokens(int tokens) {
        favorTokens=favorTokens-tokens;
    }

    /**
     * Sets <code>true</code> to the diceMoved and FirstMoveDone (if <code>false</code>) attributes of the class.
     */
    public void setDiceMoved(){
        this.diceMoved=true;
        if(!firstMoveDone) {
            firstMoveDone=true;
        }
    }

    /**
     * Sets <code>true</code> to the toolCardUsed attribute.
     */
    public void setToolCardUsed(){ toolCardUsed=true; }

    /**
     * Sets <code>true</code> to the active attribute of the class and starts a new timer for the player's timeout.
     */
    public void setActive(){
        this.active=true;
        timer.start();
    }

    /**
     * Returns <code>true</code> if the player has already placed his first dice from the draft pool.
     * @return <code>true</code> if the player has already placed a dice from the draft pool, <code>false</code> otherwise.
     */
    public boolean isFirstMoveDone() {
        return firstMoveDone;
    }

    /**
     * Returns <code>true</code> if the player is the current active player.
     * @return Returns <code>true</code> if the player is the current active player, <code>false</code> otherwise.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Returns <code>true</code> if the player has already moved a dice from the draft pool to his window frame during the current turn.
     * @return <code>true</code> if the player has already moved a dice from the draft pool to his window frame during the current turn, <code>false</code> otherwise.
     */
    public boolean isDiceMoved(){
        return this.diceMoved;
    }

    /**
     * Returns <code>true</code> if the player has already used a tool card effect during the current turn.
     * @return <code>true</code> if the player has already used a tool card effect during the current turn, <code>false</code> otherwise.
     */
    public boolean isToolCardUsed(){
        return this.toolCardUsed;
    }

    /**
     * Returns <code>true</code> if the player has already been active during the current round.
     * @return <code>true</code> if the player has already been active during the current round, <code>false</code> otherwise.
     */
    public boolean isSecondTurn() {return this.secondTurn;}

    /**
     * Returns the player's username.
     * @return the player's username.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the player's identifier.
     * @return the player's identifier.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the player's current favor tokens.
     * @return the player's current favor tokens.
     */
    public int getFavorTokens() {
        return favorTokens;
    }

    /**
     * Returns the Timer associated to this player.
     * @return the Timer associated to this player.
     */
    public Timer getTimer(){return this.timer;}

    /**
     * Returns the first {@link PrivateObjectiveCard} from the player's private card's list.
     * @return the first private objective card from the player's private card's list.
     */
    public PrivateObjectiveCard getPrivateObjectiveCard(){
        return this.privateObjectiveCards.get(0);
    }

    /**
     * Returns the private objective card from the player's private card's list at the given position.
     * @param i the private objective card's index.
     * @return the private objective card from the player's private card's list at the given position.
     */
    public PrivateObjectiveCard getPrivateObjectiveCard(int i){
        return this.privateObjectiveCards.get(i);
    }

    /**
     * Clears the player's private objective cards list.
     */
    public void resetPrivateObjectiveCard(){
        privateObjectiveCards.clear();
    }

    /**
     * Resets the diceMoved, toolCardUsed, active and secondTurn attributes to <cpde>false</cpde> and stops the associated Timer.
     */
    public void endTurn() {
        this.diceMoved=false;
        this.toolCardUsed=false;
        this.active=false;
        this.secondTurn=true;
        timer.stop();
    }

    /**
     * Resets the secondTurn and jumpSecondTurn  attributes to <code>false</code>.
     */
    public void endRound() {
        secondTurn=false;
        jumpSecondTurn=false;
    }

    /**
     * Resets the active attribute to <code>false</code> and stops the associated Timer.
     */
    public void setInactive(){
        this.active =false;
        timer.stop();
    }

    /**
     * Returns <code>true</code> if the player should not perform his second turn during the current round.
     * @return <code>true</code> if the player should not perform his second turn during the current round, <code>false</code> otherwise.
     */
    public boolean isJumpSecondTurn() {
        return jumpSecondTurn;
    }

    /**
     * Sets the jumpSecondTurn attribute to the given value.
     * @param jumpSecondTurn the value to be set as jumpSecondTurn.
     */
    public void setJumpSecondTurn(boolean jumpSecondTurn) {
        this.jumpSecondTurn = jumpSecondTurn;
    }

    /**
     * Returns the {@link Points} the player scored.
     * @return the {@link Points} the player scored, <code>null</code> if the game is not finished yet.
     */
    public Points getPoints() {
        return points;
    }


    /**
     * Calculates the points the player scored.
     * @param state the game's state.
     */
    public void calculatePoints(State state){
        for (int card=0; card<3; card++) {
            points.setPointsFromPublicCard(card, state.getPublicObjectiveCards().get(card).calculatePoints(windowFrame));
        }
        points.setPointsFromPrivateCard(privateObjectiveCards.get(0).calculatePoints(windowFrame));
        points.setPointsFromFavorTokens(favorTokens);
        points.setPointsFromEmptyCells(windowFrame.getEmptyCells());
    }

    /**
     * Returns <code>true</code> if the player has been suspended from the game.
     * @return <code>true</code> if the player has been suspended from the game, <code>false</code> otherwise.
     */
    public boolean isSuspended() {
        return suspended;
    }

    /**
     * Sets the suspended attribute to the given value, if <code>true</code> a message is print to the server console.
     * @param suspended the value to be set as suspended.
     */
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
        if(suspended)
            System.out.print(name + " disconnected.\n>>>");
    }

    /**
     * Stops the player's timer.
     */
    public void endGame() {
        timer.stop();
        suspended = true;
    }
}
