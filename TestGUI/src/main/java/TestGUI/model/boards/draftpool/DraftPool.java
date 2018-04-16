package TestGUI.model.boards.draftpool;

import TestGUI.model.bag.Bag;
import TestGUI.model.dice.Dice;
import TestGUI.model.exceptions.InvalidMoveException;

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
	public void drow(Bag bag){
	    for(int i=0; i<size; i++) {
			try {
				draftPoolCells[i].put(bag.drow());
				sleep(500);
			} catch (InvalidMoveException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
	//inserisce un dado nella riserva nel sacchetto e ne pesca uno nuovo assegnandogli un valore scelto. Ritorna il dado pescato
	public Dice redrow(Bag bag, Dice dice, int value){
		bag.insert(dice);
		return bag.drow(value);
	}

	public void clean(){
		for(DraftPoolCell cell : draftPoolCells) {
			cell.clean();
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}