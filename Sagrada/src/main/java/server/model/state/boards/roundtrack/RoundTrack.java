package server.model.state.boards.roundtrack;

import server.model.state.boards.draftpool.DraftPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Una sola istanza per la classe

public class RoundTrack{
	//Ogni casella sul tracciato dei round è rappresentato come un set di caselle poichè per ogni round posso avere più dadi
	private HashMap<Integer, ArrayList<RoundTrackCell>> roundTrackSpaces;
	public static final int MAX_ROUND=10;
	//Round attuale
	private int round;
	public RoundTrack(){
		round=1;
		roundTrackSpaces=new HashMap<>(MAX_ROUND);
		for(int i=0; i<MAX_ROUND; i++){
			roundTrackSpaces.put(i+1, new ArrayList<>());
		}
	}
	//Aumenta il round
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
	//Ritorna il set di dadi del turno indicato
	public List<RoundTrackCell> getRoundSet(int round){
		return roundTrackSpaces.get(round);
	}


	public int getRound(){
		return this.round;
	}

	public boolean isEmpty(){
		for(ArrayList list : roundTrackSpaces.values())
			if(!list.isEmpty()) return false;
		return true;
	}
}