package client.model;

import common.bag.Bag;
import common.boards.draftpool.DraftPool;
import common.boards.roundtrack.RoundTrack;
import common.objectiveCards.publicObjectiveCards.PublicObjectiveCard;
import common.toolCards.ToolCard;

public class State{
	private Bag bag;
	private DraftPool draftPool;
	private RoundTrack roundTrack;
	private final ToolCard[] toolCards;
	private final PublicObjectiveCard[] publicObjectiveCards;
	public State(){

	}


	public ToolCard getToolCard(int card){
		return toolCards[card];
	}


}