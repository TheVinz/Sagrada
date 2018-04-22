package server.state.boards.roundtrack;

import common.exceptions.InvalidMoveException;
import server.state.boards.Cell;
import server.state.boards.draftpool.DraftPool;
import server.state.dice.Dice;
import server.state.utilities.Color;
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
	public void endRound(DraftPool pool) throws InvalidMoveException {
		for(int i=pool.getSize()-1; i>=0; i--){
			if(pool.getCell(i).getDice()!=null){
				roundTrackSpaces.get(round).add(0,new RoundTrackCell());
				roundTrackSpaces.get(round).get(0).put(pool.getCell(i).removeDice());
			}
		}
	}
	//Ritorna il set di dadi del turno indicato
	public List<RoundTrackCell> getRoundSet(int round){
		return roundTrackSpaces.get(round);
	}
	//Ritorna un array dei colori dei dadi presenti sulla tabella, può tornare utile per l' effetto di alcune toolcards
	public Color[] getColors(){
		ArrayList<Color> list= new ArrayList<>();
		ArrayList<RoundTrackCell> cellList;
		for(int i=1; i<=round; i++){
			cellList=roundTrackSpaces.get(i);
			for(int j=0; j<roundTrackSpaces.get(i).size(); j++){
				if(!list.contains(cellList.get(j).getDice().getColor()))
					list.add(cellList.get(j).getDice().getColor());
			}
		}
		return list.toArray(new Color[0]);
	}
	public int getRound(){
		return this.round;
	}
}