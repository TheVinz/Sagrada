package server.state.boards.draftpool;

import server.state.bag.Bag;
import server.state.dice.Dice;
import common.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DraftPool{
	//Dimensione della riserva
	private List<DraftPoolCell> draftPoolCells = new ArrayList<>();

	public DraftPool(){
		draftPoolCells.add(new DraftPoolCell());
    }
    public void increaseSize() throws Exception{ //limite superiore?
		if(draftPoolCells.size() == 9)
			throw new Exception("The game is full");
		draftPoolCells.add(new DraftPoolCell());
		draftPoolCells.add(new DraftPoolCell());
	}
	public List<DraftPoolCell> getDraftPool(){
	    return this.draftPoolCells;
    }

	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void draw(Bag bag) throws InvalidMoveException {
	    for(int i=0; i<draftPoolCells.size(); i++) {
			draftPoolCells.get(i).put(bag.draw());
		}
    }

    public DraftPoolCell getCell(int index) {
		return draftPoolCells.get(index);
    } //manca eccezione

	public int getSize() {
		return draftPoolCells.size();
	}
}