package server.model.state.boards.draftpool;

import server.model.state.bag.Bag;
import common.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

/**
 * The <tt>DraftPool</tt> class represents the space where there are the {@link server.model.state.dice.Dice} drawn from the {@link server.model.state.bag.Bag}.
 * This is an ArrayList of {@link server.model.state.boards.draftpool.DraftPoolCell}.
 * The size of the DraftPool depends by the number of {@link server.model.state.player.Player}s.
 */
public class DraftPool{

	private List<DraftPoolCell> draftPoolCells = new ArrayList<>();

	/**
	 * Initialize a newly DraftPool with only one DraftPoolCell
	 */
	public DraftPool(){
		draftPoolCells.add(new DraftPoolCell(0));
    }

	/**
	 * Increase the size of the DraftPool by two DraftPoolCells each calls.
	 */
    public void increaseSize(){ //limite superiore?
		int size=draftPoolCells.size();
		draftPoolCells.add(new DraftPoolCell(size));
		draftPoolCells.add(new DraftPoolCell(size+1));
	}

	/**
	 * Gets the DraftPool, an ArrayList of DraftPoolCells.
	 * @return the DraftPool.
	 */
	public List<DraftPoolCell> getDraftPool(){
	    return this.draftPoolCells;
    }

	/**
	 * Fill the DraftPool by draw dices from the bag.
	 * @param bag container of the dices.
	 * @throws InvalidMoveException if the DraftPoolCell is already filled.
	 */
	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void draw(Bag bag) throws InvalidMoveException {
	    for(int i=0; i<draftPoolCells.size(); i++) {
			draftPoolCells.get(i).put(bag.draw());
		}
    }

	/**
	 * Gets the DraftPoolCell of this DraftPool at this index.
	 * @param index of the DrafPoolCell in the DraftPool.
	 * @return {@link server.model.state.boards.draftpool.DraftPoolCell}
	 */
    public DraftPoolCell getCell(int index) {
		return draftPoolCells.get(index);
    } //manca eccezione

	/**
	 * Gets the size of this DraftPool.
	 * @return the size of the DraftPool.
	 */
	public int getSize() {
		return draftPoolCells.size();
	}

	/**
	 * Verify if the DraftPool is empty.
	 * @return true if the DraftPool is empty, false if it isn't
	 */
    public boolean isEmpty() {
		for(DraftPoolCell cell : draftPoolCells)
			if(!cell.isEmpty()) return false;
		return true;
    }

	/**
	 * Increase the size of the DraftPool by one, useful for single Player.
	 */
    public void increaseSizeByOne(){
		int size=draftPoolCells.size();
		draftPoolCells.add(new DraftPoolCell(size));
	}

}