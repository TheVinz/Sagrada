package server.state.boards.draftpool;

import server.state.bag.Bag;
import server.state.dice.Dice;
import common.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DraftPool{
	//Dimensione della riserva
	private int size;
	private List<DraftPoolCell> draftPoolCells = new ArrayList<DraftPoolCell>();

	public DraftPool(){
		size=1;
		draftPoolCells.add(new DraftPoolCell());
    }
    public void increaseSize() throws Exception{ //limite superiore?
		if(size == 9)
			throw new Exception("The game is full");
		size+=2;
		draftPoolCells.add(new DraftPoolCell());
		draftPoolCells.add(new DraftPoolCell());
	}
	public List<DraftPoolCell> getDraftPool(){
	    return this.draftPoolCells;
    }

	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void draw(Bag bag){
	    for(int i=0; i<size; i++) {
			try {
				draftPoolCells.get(i).put(bag.drow());
			} catch (InvalidMoveException e) {
				e.printStackTrace();
			}
		}
    }

    public DraftPoolCell getCell(int index) {
		return draftPoolCells.get(index);
    } //manca eccezione

	public int getSize() {
		return size;
	}
}