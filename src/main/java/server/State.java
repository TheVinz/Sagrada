package server;

import common.bag.Bag;
import common.boards.draftpool.DraftPool;
import common.boards.roundtrack.RoundTrack;
import common.objectivecards.publicobjectivecards.PublicObjectiveCard;
import common.toolcards.ToolCard;

public class State{
	private Bag bag;
	private DraftPool draftPool;
	private RoundTrack roundTrack;
	private final ToolCard[] toolCards=new ToolCard[0];
	private final PublicObjectiveCard[] publicObjectiveCards=new PublicObjectiveCard[0];
	public State(){

	}


	public ToolCard getToolCard(int card){
		return toolCards[card];
	}


}