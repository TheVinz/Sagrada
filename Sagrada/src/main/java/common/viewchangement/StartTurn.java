package common.viewchangement;

import client.view.cli.CliPlayerState;
import client.view.cli.CliState;

import java.util.ArrayList;
import java.util.List;

public class StartTurn implements Changement{
    private CliPlayerState cliCurrentPlayerState;
    private List<CliPlayerState> cliPlayerStates = new ArrayList<CliPlayerState>();
    private Integer[] toolCardIds = new Integer[3];
    private Integer[] publicObjectiveCardIds = new Integer[3];
    private List<String> draftPool = new ArrayList<String>();
    private List<String> roundTrack = new ArrayList<String>();

    public StartTurn(CliPlayerState cliCurrentPlayerState,List<CliPlayerState> cliPlayerStates,Integer[] toolCardIds,Integer[] publicObjectiveCardIds,List<String> draftPool,List<String> roundTrack){
        this.cliCurrentPlayerState=cliCurrentPlayerState;
        this.cliPlayerStates=cliPlayerStates;
        this.toolCardIds=toolCardIds;
        this.publicObjectiveCardIds=publicObjectiveCardIds;
        this.draftPool=draftPool;
        this.roundTrack=roundTrack;
    }

    public List getDraftpool(){ return draftPool; }
    public List getRoundTrack(){ return roundTrack; }
    public Integer[] getToolCardIds(){return toolCardIds; }
    public Integer[] getPublicObjectiveCardIds(){ return publicObjectiveCardIds; }
    public List getCliPlayerStates(){ return cliPlayerStates; }

}
