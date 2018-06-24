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
	private final List<ToolCard> toolCards;
	private final List<PublicObjectiveCard> publicObjectiveCards;
	private final List<Player> players;
	private boolean gameFinished;

	public State(Model model){
		this.bag=new Bag();
		this.roundTrack=new RoundTrack();
		this.toolCards=new ArrayList<>();
		this.publicObjectiveCards=new ArrayList<>();
		this.players=new ArrayList<>();
		this.draftPool=new DraftPool();
		this.gameFinished = false;
	}

	public void addPlayer(Player player) {
		players.add(player);
		draftPool.increaseSize();

	}

	public DraftPool getDraftPool(){
		return this.draftPool;
	}
	public ToolCard getToolCard(int card){
		return toolCards.get(card);
	}
	public List<ToolCard> getToolCards(){
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
	public List<PublicObjectiveCard> getPublicObjectiveCards() {
		return this.publicObjectiveCards;
	}

	public boolean isGameFinished() {
		return gameFinished;
	}

	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}
}