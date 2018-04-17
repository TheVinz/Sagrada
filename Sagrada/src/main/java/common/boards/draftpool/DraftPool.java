package common.boards.draftpool;

import common.bag.Bag;
import common.dice.Dice;
import common.exceptions.InvalidMoveException;

//Nel nostro programma la classe Riserva istanzierà un solo oggetto: Singleton? o altri pattern?

public class DraftPool{
	//Dimensione della riserva
	private final int size;
	private DraftPoolCell[] draftPoolCells;
	public DraftPool(int size){
	    this.size=size;
	    this.draftPoolCells=new DraftPoolCell[size];
    }
	public DraftPoolCell[] getDraftPool(){
	    return this.draftPoolCells;
    }
	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void drow(Bag bag){
	    for(int i=0; i<size; i++) {
			try {
				draftPoolCells[i].put(bag.drow());
			} catch (InvalidMoveException e) {
				e.printStackTrace();
			}
		}
    }
	//inserisce un dado nella riserva nel sacchetto e ne pesca uno nuovo assegnandogli un valore scelto. Ritorna il dado pescato
	public Dice redrow(Bag bag, Dice dice, int value){
		bag.insert(dice);
		return bag.drow(value);
	}
}