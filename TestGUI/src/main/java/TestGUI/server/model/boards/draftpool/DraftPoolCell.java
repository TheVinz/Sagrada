package TestGUI.server.model.boards.draftpool;

import TestGUI.server.model.boards.Cell;
import TestGUI.server.model.dice.Dice;
import TestGUI.server.model.exceptions.InvalidMoveException;

public class DraftPoolCell implements Cell {
	private Dice dice;
	private int index;

	public DraftPoolCell(int index){
		this.index=index;
	}

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

	public int getIndex() {
		return index;
	}

	@Override
	public String asImage() {
		if(!isEmpty()){
			return dice.asImage();
		}
		else return "File:resources/icons/void.png";
	}

	@Override
	public Dice removeDice() throws InvalidMoveException {
		if(this.dice==null) throw new InvalidMoveException("Empty cell");
		Dice dice=this.dice;
		this.dice=null;
		return dice;
	}
}