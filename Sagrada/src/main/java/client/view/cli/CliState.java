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
    public CliState(){

    }

    public CliState(List<String> draftPool,List<String> roundTrack,Integer[] toolCardIds,Integer[] publicObjectiveCardIds,List<CliPlayerState> cliPlayerStates){
        this.draftPool=draftPool;
        this.roundTrack=roundTrack;
        this.toolCardIds=toolCardIds;
        this.publicObjectiveCardIds=publicObjectiveCardIds;
        this.cliPlayerStates=cliPlayerStates;
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

    public List<String> getRoundTrack() {
        return roundTrack;
    }

    public CliPlayerState getCliPlayerState(String name) {
        for(int i=0; i<cliPlayerStates.size(); i++)
            if(cliPlayerStates.get(i).getName().equals(name))
                return cliPlayerStates.get(i);
        return cliPlayerStates.get(0); //da togliere
    }

    //public void setDraftPool(List<String> draftPool) {
  //      this.draftPool = draftPool;
//    }

    //public void setCliPlayerStates(List<CliPlayerState> cliPlayerStates) {
  //      this.cliPlayerStates=cliPlayerStates;
//    }

    //public void setPublicObjectiveCardIds(Integer[] publicObjectiveCardIds) {
  //      this.publicObjectiveCardIds = publicObjectiveCardIds;
//    }

//    public void setToolCardIds(Integer[] toolCardIds) {
      //  this.toolCardIds = toolCardIds;
    //}
    //public void setRoundTrack(List<String> roundTrack){
   //     this.roundTrack=roundTrack;
   // }

    public CliPlayerState getCliCurrentPlayerState() {
        return cliCurrentPlayerState;
    }
}
