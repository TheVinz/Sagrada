package client.view.cli;

import java.util.ArrayList;
import java.util.List;

public class CliState {
    private CliPlayerState activePlayer;
    private List<CliPlayerState> cliPlayerStates = new ArrayList<>();
    private Integer[] toolCardIds = new Integer[3];
    private Integer[] publicObjectiveCardIds = new Integer[3];
    private List<String> draftPool = new ArrayList<>();
    private List<List <String>> roundTrack = new ArrayList<>();
    private String privateObjectiveCard;

    public void setActivePlayer(int id){
        activePlayer=getCliPlayerState(id);
    }
    public void addPlayer(CliPlayerState player){
        cliPlayerStates.add(player);
    }
    public void setPrivateObjectiveCard(String color){
        this.privateObjectiveCard=color;
    }

    public Integer[] getToolCardIds() {
        return toolCardIds;
    }
    public Integer[] getPublicObjectiveCardIds() {
        return publicObjectiveCardIds;
    }
    public List<String> getDraftPool() {
        return draftPool;
    }
    public List<List <String>> getRoundTrack() {
        return roundTrack;
    }
    public CliPlayerState getCliPlayerState(int id) {
        for(int i=0; i<cliPlayerStates.size(); i++)
            if(cliPlayerStates.get(i).getId() == id)
                return cliPlayerStates.get(i);
        return cliPlayerStates.get(0); //da togliere
    }
    public CliPlayerState getActivePlayer() {
        return activePlayer;
    }
    public String getPrivateObjectiveCard(){
        return this.privateObjectiveCard;
    }
}
