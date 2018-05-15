package server.model.state.boards;
import common.ModelObject;
import server.model.state.dice.Dice;
import common.exceptions.InvalidMoveException;

public abstract class Cell implements ModelObject {
	private Dice dice;

	public Dice getDice(){
		return this.dice;
	}

	public void move(Cell target) throws InvalidMoveException{
		if(this.isEmpty())
			throw new InvalidMoveException("Empty cell");
		else {
			target.put(dice);
			dice = null;
		}
	}

	public void put(Dice dice) throws InvalidMoveException{
		if(!this.isEmpty())
			throw new InvalidMoveException("Already filled cell");
		this.dice=dice;
	}

	public boolean isEmpty(){
		return dice==null;
	}

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