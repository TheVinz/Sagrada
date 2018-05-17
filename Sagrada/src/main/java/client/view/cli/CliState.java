package client.view.cli;

import client.view.cli.cliphasestate.CliPhaseState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CliState {
    private CliPlayerState activePlayer;
    private CliPlayerState[] cliPlayerStates;
    private Integer[] toolCardIds = new Integer[3];
    private Integer[] publicObjectiveCardIds = new Integer[3];
    private String[] draftPool;
    private String[][] roundTrack = new String[10][];
    private String privateObjectiveCard;

    public void setActivePlayer(int id){
        activePlayer=getCliPlayerState(id);
    }
    public void setPrivateObjectiveCard(String color){
        this.privateObjectiveCard=color;
    }
    public void setCliPlayerStates(CliPlayerState[] players) {
        this.cliPlayerStates=players;
    }
    public void setDraftPool(String[] dices) {
        this.draftPool=dices;
    }

    public Integer[] getToolCardIds() {
        return toolCardIds;
    }
    public Integer[] getPublicObjectiveCardIds() {
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
            if(cps.getName() == name)
                return cps;
        return null; //da togliere
    }
    public String getPrivateObjectiveCard(){
        return this.privateObjectiveCard;
    }

    public CliPlayerState getActivePlayer() {
        return activePlayer;
    }
    public void setRoundDices(int round, String[] roundDices) {
        roundTrack[round]=roundDices;
    }
}
