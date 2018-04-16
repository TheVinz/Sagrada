package server;


public class PlayerModel {

	private int favorTokens;
	private WindowFrame windowFrame;
	private PrivateObjectiveCard privateObjectiveCard;
	private WindowFrame[] windowFrameChoises;
	public PlayerModel(WindowFrame[] wfc, PrivateObjectiveCard poc){
		this.windowFrameChoises=wfc;
		this.privateObjectiveCard=poc;
	}

	public void setFrame(int i){
		if(windowFrame==null) windowFrame=windowFrameChoises[i];
	}

	public String getFrameState(){
		return windowFrame.getState();
	}

	public WindowFrameSpace getWindowFrameSpace(int i, int j){
		return windowFrame.get(i,j);
	}

	@Override
	public String toString(){
		return this.name;
	}
}