package client.view.cli;

import client.view.cli.cliphasestate.CliPhaseState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CliState {
    private CliPlayerState activePlayer;
    private CliPlayerState[] cliPlayerStates;
    private int[] toolCardIds;
    private int[] publicObjectiveCardIds;
    private String[] draftPool;
    private String[][] roundTrack = new String[10][];
    private List<String> privateObjectiveCard = new ArrayList<>();
    private int round = 1;
    private boolean gameFinished = false;

    private static CliState cliState;

    private CliState(){};

    public static CliState getCliState(){
        if(cliState == null)
            cliState = new CliState();
        return cliState;
    }

    public void resetCliState(){
        cliState = new CliState();
    }
    public void resetPrivate(){
        privateObjectiveCard.clear();
    }
    public void setActivePlayer(int id){
        activePlayer=getCliPlayerState(id);
    }
    public void setPrivateObjectiveCard(String color){
        privateObjectiveCard.add(color);
    }
    public void setCliPlayerStates(CliPlayerState[] players) {
        this.cliPlayerStates=players;
    }
    public void setDraftPool(String[] dices) {
        this.draftPool=dices;
    }

    public int[] getToolCardIds() {
        return toolCardIds;
    }
    public int[] getPublicObjectiveCardIds() {
        return publicObjectiveCardIds;
    }
    public String[] getDraftPool() {
        return draftPool;
    }
    public String[][] getRoundTrack() {
        return roundTrack;
    }
    public CliPlayerState getCliPlayerState(int id) {
        for(CliPlayerState cps : this.cliPlayerStates)
            if(cps.getId() == id)
                return cps;
        return null; //da togliere
    }
    public CliPlayerState getCliPlayerState(String name) {
        for(CliPlayerState cps : this.cliPlayerStates)
            if(cps.getName().equals(name))
                return cps;
        return null; //da togliere
    }
    public CliPlayerState getCliPlayerState() {
        for(CliPlayerState cps : this.cliPlayerStates)
            if(cps.getId() == CliApp.getCliApp().getId())
                return cps;
        return null; //da togliere
    }
    public String[] getPrivateObjectiveCard(){
        return this.privateObjectiveCard.toArray(new String[0]);
    }

    public CliPlayerState getActivePlayer() {
        return activePlayer;
    }
    public void setRoundDices(int round, String[] roundDices) {
        System.out.println("DEBUG -> "  + round);
        this.round=round+1;
        roundTrack[round-1]=roundDices;
    }


    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setToolCardIds(int[] toolCardIds) {
        this.toolCardIds = toolCardIds;
    }

    public void setPublicObjectiveCardIds(int[] publicObjectiveCardIds) {
        this.publicObjectiveCardIds = publicObjectiveCardIds;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
}
