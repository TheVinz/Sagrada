package common.boards.roundtrack;

import common.boards.Cell;
import common.dice.Dice;
import common.utilities.Color;
import java.util.ArrayList;
import java.util.Hashtable;

//Una sola istanza per la classe

public class RoundTrack{
	//Ogni casella sul tracciato dei round è rappresentato come un set di caselle poichè per ogni round posso avere più dadi
	private Hashtable<Integer, ArrayList<RoundTrackCell>> roundTrackSpaces;
	private final int size=10;
	//Round attuale
	private int round;
	public RoundTrack(){
		round=1;
		roundTrackSpaces=new Hashtable<>(size);
		for(int i=0; i<size; i++){
			roundTrackSpaces.put(i+1, new ArrayList<>());
		}
	}
	//Aumenta il round
	public void newRound(){
		round++;
	}
	//Aggiunge un dado al set di dadi del turno attuale
	public void add(Dice dice){
		roundTrackSpaces.get(round).add(new RoundTrackCell(dice));
	}
	//Ritorna il set di dadi del turno indicato
	public Cell[] getRoundSet(int round){
		return roundTrackSpaces.get(round).toArray(new Cell[0]);
	}
	//Ritorna un array dei colori dei dadi presenti sulla tabella, può tornare utile per l' effetto di alcune toolcards
	public Color[] getColors(){
	}
	public int getRound(){}
}