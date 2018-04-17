package server;


import common.boards.windowframe.WindowFrame;
import common.boards.windowframe.WindowFrameCell;
import common.objectivecards.privateobjectivecards.PrivateObjectiveCard;

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



	public WindowFrameCell getWindowFrameSpace(int i, int j){
		return windowFrame.getCell(i,j);
	}

}