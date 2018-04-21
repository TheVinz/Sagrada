package server.state.boards.draftpool;

import server.state.bag.Bag;
import server.state.dice.Dice;
import common.exceptions.InvalidMoveException;

public class DraftPool{
	//Dimensione della riserva
	private int size;
	private DraftPoolCell[] draftPoolCells;

	public DraftPool(){
		size=1;
    }
    public void increaseSize(){
		size+=2;
		draftPoolCells=new DraftPoolCell[size];
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

    public DraftPoolCell getCell(int index) {
		return draftPoolCells[index];
    }
}