package server.model.state;

import server.model.state.bag.Bag;
import server.model.state.boards.draftpool.DraftPool;
import server.model.state.boards.roundtrack.RoundTrack;
import server.model.state.boards.windowframe.WindowFrame;
import server.model.state.objectivecards.publicobjectivecards.PublicObjectiveCard;
import server.model.state.player.Player;
import server.model.state.toolcards.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The <tt>State</tt> class contains all the data about a game.
 */
public class State{
	private final Bag bag;
	private final DraftPool draftPool;
	private final RoundTrack roundTrack;
	private final List<ToolCard> toolCards;
	private final List<PublicObjectiveCard> publicObjectiveCards;
	private final List<Player> players;
	private boolean gameFinished;

	/**
	 * Initializes a new <tt>State</tt> with his own {@link Bag}, {@link RoundTrack} and {@link DraftPool}.
	 */
	public State(){
		this.bag=new Bag();
		this.roundTrack=new RoundTrack();
		this.toolCards=new ArrayList<>();
		this.publicObjectiveCards=new ArrayList<>();
		this.players=new ArrayList<>();
		this.draftPool=new DraftPool();
		this.gameFinished = false;
	}

	/**
	 * Adds a {@link Player} to the <tt>State</tt>'s player list.
	 * @param player the player added to the state.
	 */
	public void addPlayer(Player player) {
		players.add(player);
		draftPool.increaseSize();

	}

	/**
	 * Returns the <tt>State</tt>'s draft pool.
	 * @return the <tt>State</tt>'s draft pool.
	 */
	public DraftPool getDraftPool(){
		return this.draftPool;
	}

	/**
	 * Returns the {@link ToolCard} with the specified index among the cards drawn for the game.
	 * @param card the tool card's index.
	 * @return the tool card with the specified index.
	 */
	public ToolCard getToolCard(int card){
		return toolCards.get(card);
	}

	/**
	 * Returns the list of all the cards drawn for the game.
	 * @return the list of the game's tool cards.
	 */
	public List<ToolCard> getToolCards(){
		return this.toolCards;
	}

	/**
	 * Returns the Player with the specified identifier.
	 * @param id the player identifier.
	 * @return the player with the specified id.
	 */
	public Player getPlayer(int id){
		return players.get(id);
	}

	/**
	 * Returns a list of all the player in the game.
	 * @return a list of all the player in the game.
	 */
	public List<Player> getPlayers(){
		return this.players;
	}

	/**
	 * Returns {@link WindowFrame} of the player with the specified index.
	 * @param index the player's index.
	 * @return the window frame of the specified player.
	 */
	public WindowFrame getWindowFrame(int index){
		return players.get(index).getWindowFrame();
	}

	/**
	 * Returns the game's round track.
	 * @return the game's round track.
	 */
	public RoundTrack getRoundTrack() { return roundTrack; }

	/**
	 * Returns the game's bag.
	 * @return the game's bag.
	 */
	public Bag getBag() { return this.bag; }

	/**
	 * Returns a list of the {@link PublicObjectiveCard}s drawn for the game.
	 * @return the game's public objective cards list.
	 */
	public List<PublicObjectiveCard> getPublicObjectiveCards() {
		return this.publicObjectiveCards;
	}

	/**
	 * Returns <code>true</code> if the game finished.
	 * @return <code>true</code> if the game finisced, <code>false</code> otherwise.
	 */
	public boolean isGameFinished() {
		return gameFinished;
	}

	/**
	 * Sets the gameFinished attribute to the specified value.
	 * @param gameFinished the value to be set as gameFinished.
	 */
	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}
}