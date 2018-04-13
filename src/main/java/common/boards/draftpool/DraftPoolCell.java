package common.boards.draftpool;

import common.dice.Dice;
import common.boards.Cell;
import common.exceptions.InvalidMoveException;

public class DraftPoolCell implements Cell{
	private Dice dice;

	public Dice getDice(){
		return this.dice;
	}

	public void move(Cell target) throws InvalidMoveException{
		if(this.isEmpty()) throw new InvalidMoveException("Empty cell");
		else {
			target.put(dice);
			dice = null;
		}
	}

	public void put(Dice dice) throws InvalidMoveException{
		if(!this.isEmpty()) throw new InvalidMoveException("Already filled cell");
		else{
			this.dice=dice;
		}
	}

	public boolean isEmpty(){
		return dice==null;
	}
}