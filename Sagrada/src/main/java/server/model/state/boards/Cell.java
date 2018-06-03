package server.model.state.boards;
import server.model.state.ModelObject.ModelObject;
import server.model.state.dice.Dice;
import common.exceptions.InvalidMoveException;

/**
 * Cell is the abstract base class for all the different cells in the game.
 * Implements the methods that manage the movement of {@link server.model.state.dice.Dice}.
 */
public abstract class Cell implements ModelObject {
	private Dice dice;

	/**
	 * Gets the dice in this cell.
	 * @return the dice in the cell.
	 */
	public Dice getDice(){
		return this.dice;
	}

	/**
	 * Move a dice from this cell to another one, target.
	 * @param target cell where you want to move the dice.
	 * @throws InvalidMoveException if there isn't a dice in this cell.
	 */
	public void move(Cell target) throws InvalidMoveException{
		if(this.isEmpty())
			throw new InvalidMoveException("Empty cell");
		else {
			target.put(dice);
			dice = null;
		}
	}

	/**
	 * Put a dice in this cell.
	 * @param dice to put in this cell.
	 * @throws InvalidMoveException if the cell is Already filled.
	 */
	public void put(Dice dice) throws InvalidMoveException{
		if(!this.isEmpty())
			throw new InvalidMoveException("Already filled cell");
		this.dice=dice;
	}

	/**
	 * Verify if this cell is empty or filled.
	 * @return true if this cell is empty, false if it isn't.
	 */
	public boolean isEmpty(){
		return dice==null;
	}

	/**
	 * Remove a dice from this cell.
	 * @return dice that was in this cell.
	 * @throws InvalidMoveException if this cell is empty.
	 */
	public Dice removeDice() throws  InvalidMoveException{
		if(this.isEmpty())
			throw new InvalidMoveException("Empty cell");
		else {
			Dice result = this.dice;
			this.dice = null;
			return result;
		}
	}
}