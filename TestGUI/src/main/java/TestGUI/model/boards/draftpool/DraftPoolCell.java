package TestGUI.model.boards.draftpool;

import TestGUI.model.boards.Cell;
import TestGUI.model.dice.Dice;
import TestGUI.model.exceptions.InvalidMoveException;
import TestGUI.model.utilities.CellObserver;

public class DraftPoolCell implements Cell {
	private Dice dice;
	private CellObserver observer;
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
			notifyObserver();
		}
	}

	public void put(Dice dice) throws InvalidMoveException{
		if(!this.isEmpty()) throw new InvalidMoveException("Already filled cell");
		else{
			this.dice=dice;
			notifyObserver();
		}
	}

	public void clean(){
		this.dice=null;
		notifyObserver();
	}

	public boolean isEmpty(){
		return dice==null;
	}

	@Override
	public String asImage() {
		if(!isEmpty()){
			return dice.asImage();
		}
		else return "File:resources/icons/void.png";
	}

	@Override
	public void addObserver(CellObserver observer) {
		this.observer=observer;
	}

	@Override
	public void notifyObserver() {
		if(observer != null) observer.updateDraftPoolCell(index);
	}
}