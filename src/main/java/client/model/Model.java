package client.model;

import client.view.View;
import common.toolCards.ToolCard;

public class Model{
	private State state;
	private View view;

	public Model(){
	}
	public void setView(View view){
		this.view=view;
	}


	public ToolCard getToolCard(int card){
		return state.getToolCard(card);
	}


}