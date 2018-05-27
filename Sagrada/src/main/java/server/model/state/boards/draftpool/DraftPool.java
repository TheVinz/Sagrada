package server.model.state.boards.draftpool;

import server.model.state.bag.Bag;
import common.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

public class DraftPool{
	//Dimensione della riserva
	private List<DraftPoolCell> draftPoolCells = new ArrayList<>();

	public DraftPool(){
		draftPoolCells.add(new DraftPoolCell(0));
    }
    public void increaseSize() throws Exception{ //limite superiore?
		if(draftPoolCells.size() == 9)
			throw new Exception("The game is full");
		int size=draftPoolCells.size();
		draftPoolCells.add(new DraftPoolCell(size));
		draftPoolCells.add(new DraftPoolCell(size+1));
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

    public boolean isEmpty() {
		for(DraftPoolCell cell : draftPoolCells)
			if(!cell.isEmpty()) return false;
		return true;
    }
}