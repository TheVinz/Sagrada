package server.model.state;

import server.model.state.bag.Bag;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
import server.model.state.toolcards.ToolCard;
import server.model.state.utilities.Util;
import server.model.Model;

import java.util.ArrayList;
import java.util.List;

public class State{
	private final Bag bag;
	private final DraftPool draftPool;
	private final RoundTrack roundTrack;
	private final ToolCard[] toolCards;
	private final PublicObjectiveCard[] publicObjectiveCards;
	private final List<Player> players;
	private final RoundManager roundManager;

	public State(Model model){
		this.bag=new Bag();
		this.roundTrack=new RoundTrack();
		this.toolCards=Util.getToolCards(model);
		this.publicObjectiveCards=Util.getPublicObjectiveCards();
		this.players=new ArrayList<>();
		this.draftPool=new DraftPool();
		this.roundManager=new RoundManager();
	}

	public void addPlayer(Player player) throws Exception{
		players.add(player);
		draftPool.increaseSize();

	}

	public DraftPool getDraftPool(){
		return this.draftPool;
	}
	public ToolCard getToolCard(int card){
		return toolCards[card];
	}
	public ToolCard[] getToolCards(){
		return this.toolCards;
	}
	public Player getPlayer(int id){
		return players.get(id);
	}
	public List<Player> getPlayers(){
		return this.players;
	}
	public WindowFrame getWindowFrame(int index){
		return players.get(index).getWindowFrame();
	}
	public RoundTrack getRoundTrack() { return roundTrack; }
	public Bag getBag() { return this.bag; }
}