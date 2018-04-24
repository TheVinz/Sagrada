package server.state;

import server.state.player.Player;
import server.Model;
import server.state.bag.Bag;
import server.state.boards.draftpool.DraftPool;
import server.state.boards.roundtrack.RoundTrack;
import server.state.boards.windowframe.WindowFrame;
import server.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.state.toolcards.ToolCard;
import server.state.utilities.Util;

import java.util.ArrayList;
import java.util.List;

public class State{
	private final Bag bag;
	private final DraftPool draftPool;
	private final RoundTrack roundTrack;
	private final ToolCard[] toolCards;
	private final PublicObjectiveCard[] publicObjectiveCards;
	private final List<Player> players;

	public State(Model model){
		this.bag=new Bag();
		this.roundTrack=new RoundTrack();
		this.toolCards=Util.getToolCards(model);
		this.publicObjectiveCards=Util.getPublicObjectiveCards();
		this.players=new ArrayList<>();
		this.draftPool=new DraftPool();
	}

	public void addPlayer(String name, int id) throws Exception{
		players.add(id, new Player(name, id));
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