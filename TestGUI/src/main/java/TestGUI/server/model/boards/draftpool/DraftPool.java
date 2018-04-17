package TestGUI.server.model.boards.draftpool;

import TestGUI.server.model.bag.Bag;
import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.dice.Dice;
import TestGUI.server.model.exceptions.InvalidMoveException;

import static java.lang.Thread.sleep;


//Nel nostro programma la classe Riserva istanzierà un solo oggetto: Singleton? o altri pattern?

public class DraftPool{
	//Dimensione della riserva
	private final int size;
	private DraftPoolCell[] draftPoolCells;
	public DraftPool(int size){
	    this.size=size;
	    this.draftPoolCells=new DraftPoolCell[size];
	    for(int i=0; i<size; i++) draftPoolCells[i]=new DraftPoolCell(i);
    }
	public DraftPoolCell[] getDraftPool(){
	    return this.draftPoolCells;
    }
	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void drow(Bag bag) throws InvalidMoveException {
	    for(int i=0; i<size; i++)
	    	draftPoolCells[i].put(bag.drow());
    }
	//inserisce un dado nella riserva nel sacchetto e ne pesca uno nuovo assegnandogli un valore scelto. Ritorna il dado pescato
	public Dice redrow(Bag bag, Dice dice, int value){
		bag.insert(dice);
		return bag.drow(value);
	}

    public Cell get(Integer index) {
		return this.draftPoolCells[index];
    }

	public boolean isEmpty() {
		boolean result=true;
		for(int i=0; i<size; i++)result = result && get(i).isEmpty();
		return result;
	}
}