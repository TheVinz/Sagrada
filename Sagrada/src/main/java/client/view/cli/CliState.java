package client.view.cli;


import java.util.ArrayList;
import java.util.List;

public class CliState {

    private CliPlayerState cliCurrentPlayerState;
    private List<CliPlayerState> cliPlayerStates = new ArrayList<CliPlayerState>();
    private Integer[] toolCardIds = new Integer[3];
    private Integer[] publicObjectiveCardIds = new Integer[3];
    private List<String> draftPool = new ArrayList<String>();
    private List<String> roundTrack = new ArrayList<String>();

    public Integer[] getToolCardIds() {
        return toolCardIds;
    }

    public Integer[] getPublicObjectiveCardIds() {
        return publicObjectiveCardIds;
    }

    public List<String> getDraftPool() {
        return draftPool;
    }

    public List<String> getRoundTrack() {
        return roundTrack;
    }

    public CliPlayerState getCliPlayerState(String name) throws Exception{
        for(int i=0; i<cliPlayerStates.size(); i++)
            if(cliPlayerStates.get(i).getName().equals(name))
                return cliPlayerStates.get(i);
        throw new Exception("player not found");
    }


    public CliPlayerState getCliCurrentPlayerState() {
        return cliCurrentPlayerState;
    }
}
