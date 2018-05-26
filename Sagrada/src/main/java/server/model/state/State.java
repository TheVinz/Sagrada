package server.model.state;

import server.model.state.bag.Bag;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
import server.model.state.toolcards.*;
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

	public State(Model model){
		this.bag=new Bag();
		this.roundTrack=new RoundTrack();
		this.toolCards= new ToolCard[3];
		this.toolCards[0] = new DiluentePerPastaSalda(model);
		this.toolCards[1] = new PennelloperPastaSalda(model);
		this.toolCards[2] = new TenagliaARotelle(model);
		this.publicObjectiveCards=Util.getPublicObjectiveCards();
		this.players=new ArrayList<>();
		this.draftPool=new DraftPool();
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
	public PublicObjectiveCard[] getPublicObjectiveCards() {
		return this.publicObjectiveCards;
	}
}