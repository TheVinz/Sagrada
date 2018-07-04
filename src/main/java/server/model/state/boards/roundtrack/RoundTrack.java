package server.model.state.boards.roundtrack;

import server.model.state.boards.draftpool.DraftPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * The <tt>RoundTrack</tt> class contains the remaining {@link server.model.state.dice.Dice} in the {@link server.model.state.boards.draftpool.DraftPool } at the end of the round.
 * This is a HashMap of ArrayList of {@link server.model.state.boards.roundtrack.RoundTrackCell}.
 * His size is 10, number of the round of the game.
 */
public class RoundTrack{

	private HashMap<Integer, ArrayList<RoundTrackCell>> roundTrackSpaces;
	public static final int MAX_ROUND=10;
	private int round;

	/**
	 * Initialize the RoundTrack, create ten ArrayList, each one for one round.
	 */
	public RoundTrack(){
		round=1;
		roundTrackSpaces=new HashMap<>(MAX_ROUND);
		for(int i=0; i<MAX_ROUND; i++){
			roundTrackSpaces.put(i+1, new ArrayList<>());
		}
	}

	/**
	 * This put at the end of the n-round the dice from the DraftPool in the ArrayList corresponding at the n-round.
	 * After this, increments the round to n+1.
	 * @param pool the DraftPool with the dice.
	 * @throws Exception if the round is 10, because the game is finished.
	 */
	public void endRound(DraftPool pool) throws Exception {
		if(round == 11)
			throw new Exception("Game is finished");
		int index=0;
		for(int i=0; i<pool.getSize(); i++){
			if(pool.getCell(i).getDice()!=null){
				roundTrackSpaces.get(round).add(index, new RoundTrackCell(round, index));
				roundTrackSpaces.get(round).get(index).put(pool.getCell(i).removeDice());
				index++;
			}
		}
		round++;
	}

	/**
	 * Gets the ArrayList of RoundTrackCells of the round indicated.
	 * @param round to get the correct ArrayList and not all this RoundTrack.
	 * @return ArrayList of RoundTrackCells of the round indicated.
	 */
	public List<RoundTrackCell> getRoundSet(int round){
		return roundTrackSpaces.get(round);
	}


	/**
	 * Gets the current round of the game.
	 * @return the current round.
	 */
	public int getRound(){
		return this.round;
	}

	/**
	 * Verify if the RoundTrack is empty.
	 * @return false if there is a dice in one of the ArrayList, true if there isn't.
	 */
	public boolean isEmpty(){
		for(ArrayList list : roundTrackSpaces.values())
			if(!list.isEmpty()) return false;
		return true;
	}

	/**
	 * Sum the value of every dice in the RoundTrack, used to calculate the Objective Points for Single Player version.
	 * @return the sum of every value of the dice in the RoundTrack
	 */
    public int calculatePoints() {
		int points=0;
		for(int round=0;round<MAX_ROUND;round++){
			if(roundTrackSpaces.get(round)!=null){
			for(int index=0;index<roundTrackSpaces.get(round).size();index++){
				points=roundTrackSpaces.get(round).get(index).getDice().getValue()+points;
			}}
		}
		return points;
    }
}