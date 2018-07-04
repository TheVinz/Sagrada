package client.view.cli;

import client.view.cli.cliphasestate.CliPhaseState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The <tt>CliState</tt> class indicates the state of a game in the cli. The state is given by the {@link server.model.state.player.Player}s,
 * the {@link server.model.state.toolcards.ToolCard}s, the {@link server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard}s,
 * the {@link server.model.state.boards.draftpool.DraftPool}, the {@link server.model.state.boards.roundtrack.RoundTrack} and
 * the {@link server.model.state.objectivecards.privateobjectivecards.PrivateObjectiveCard}s in the game.
 * The state also indicates which Player is playing.
 */
public class CliState {
    private CliPlayerState activePlayer;
    private CliPlayerState[] cliPlayerStates;
    private int[] toolCardIds;
    private boolean[] toolCardUsed;
    private int[] publicObjectiveCardIds;
    private String[] draftPool;
    private String[][] roundTrack = new String[10][];
    private List<String> privateObjectiveCard = new ArrayList<>();
    private int round = 1;
    private boolean gameFinished = false;

    private static CliState cliState;

    private CliState(){};

    /**
     * Gets the actual state of a game.
     * @return the CliState, the state of the game.
     */
    public static CliState getCliState(){
        if(cliState == null)
            cliState = new CliState();
        return cliState;
    }

    /**
     * Initialize a new CliState.
     */
    public void resetCliState(){
        cliState = new CliState();
    }

    /**
     * Resets the PrivateObjectiveCard.
     */
    public void resetPrivate(){
        privateObjectiveCard.clear();
    }

    /**
     * Set the Player who's playing.
     * @param id the id of the Player who's playing.
     */
    public void setActivePlayer(int id){
        activePlayer=getCliPlayerState(id);
    }

    /**
     * Set the PrivateObjectiveCard.
     * @param color color of the PrivateObjectiveCard.
     */
    public void setPrivateObjectiveCard(String color){
        privateObjectiveCard.add(color);
    }

    /**
     * Set an array of CliPlayerStates which represents the states of the Player in the game.
     * @param players an array of CliPlayerState.
     */
    public void setCliPlayerStates(CliPlayerState[] players) {
        this.cliPlayerStates=players;
    }

    /**
     * Set the DraftPool in a new round.
     * @param dices an array which represents the {@link server.model.state.dice.Dice} in the DraftPool.
     */
    public void setDraftPool(String[] dices) {
        this.draftPool=dices;
    }

    /**
     * Gets the ToolCards in the game.
     * @return an array of int which represents the ToolCards in a game.
     */
    public int[] getToolCardIds() {
        return toolCardIds;
    }

    /**
     * Gets the PublicObjectiveCards in the game.
     * @return an array of int which represents the PublicObjectiveCards in a game.
     */
    public int[] getPublicObjectiveCardIds() {
        return publicObjectiveCardIds;
    }

    /**
     * Gets the DraftPool in the game.
     * @return an array of String which represents the Dice in the DraftPool.
     */
    public String[] getDraftPool() {
        return draftPool;
    }

    /**
     * Gets the RoundTrack in the game.
     * @return a matrix of String which represents the round and the Dice of the round in the RoundTrack.
     */
    public String[][] getRoundTrack() {
        return roundTrack;
    }

    /**
     * Gets the CliPlayerState of a Player given the id.
     * @param id the id of the Player which state will be returned.
     * @return the CliPlayerState of the Player with the id assigned.
     */
    public CliPlayerState getCliPlayerState(int id) {
        for(CliPlayerState cps : this.cliPlayerStates)
            if(cps.getId() == id)
                return cps;
        return null; //da togliere
    }

    /**
     * Gets the CliPlayerState of a Player given the name.
     * @param name the name of the Player which state will be returned.
     * @return the CliPlayerState of the Player with the name assigned.
     */
    public CliPlayerState getCliPlayerState(String name) {
        for(CliPlayerState cps : this.cliPlayerStates)
            if(cps.getName().equals(name))
                return cps;
        return null; //da togliere
    }

    /**
     * Gets the state of the active Player.
     * @return the CliPlayerState of the ActivePlayer.
     */
    public CliPlayerState getCliPlayerState() {
        for(CliPlayerState cps : this.cliPlayerStates)
            if(cps.getId() == CliApp.getCliApp().getId())
                return cps;
        return null; //da togliere
    }

    /**
     * Gets the PrivateObjectiveCards.
     * @return an array which represents the PrivateObjectiveCard.
     */
    public String[] getPrivateObjectiveCard(){
        return this.privateObjectiveCard.toArray(new String[0]);
    }

    /**
     * Gets the active Player.
     * @return the CliPlayerState of the active Player.
     */
    public CliPlayerState getActivePlayer() {
        return activePlayer;
    }

    /**
     * Set the Dice of a round in the RoundTrack.
     * @param round the round.
     * @param roundDices an array of String which indicates the Dice to put in the RoundTrack in the given round.
     */
    public void setRoundDices(int round, String[] roundDices) {
        this.round=round+1;
        roundTrack[round-1]=roundDices;
    }

    /**
     * Gets the actual round of the game.
     * @return an int which represents the actual round of the game.
     */
    public int getRound() {
        return round;
    }

    /**
     * Set the round of the game.
     * @param round an int which represents the new round of the game.
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Set the ToolCards in the game.
     * @param toolCardIds an array of int which represents the ToolCards in the game.
     */
    public void setToolCardIds(int[] toolCardIds) {
        this.toolCardUsed = new boolean[toolCardIds.length];
        for(int i=0; i<toolCardIds.length; i++){
            toolCardUsed[i]=false;
        }
        this.toolCardIds = toolCardIds;
    }

    /**
     * Set the PublicObjectiveCards in the game.
     * @param publicObjectiveCardIds an array of int which represents the PublicObjectiveCards in the game.
     */
    public void setPublicObjectiveCardIds(int[] publicObjectiveCardIds) {
        this.publicObjectiveCardIds = publicObjectiveCardIds;
    }

    /**
     * Indicates if the game is finished or not.
     * @return true if the game is finished, false if it isn't.
     */
    public boolean isGameFinished() {
        return gameFinished;
    }

    /**
     * Set the boolean which indicates if the game is finished or not.
     * @param gameFinished a boolean which is true if the game is finished, false if it isn't.
     */
    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    /**
     * Gets an array of boolean which indicates if a ToolCard has been used.
     * @return an array of boolean which indicates if a ToolCard has been used, true if it is false if it isn't.
     */
    public boolean[] getToolCardUsed() {
        return toolCardUsed;
    }

}
