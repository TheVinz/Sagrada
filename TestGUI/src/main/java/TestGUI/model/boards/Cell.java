package TestGUI.model.boards;

import TestGUI.model.dice.Dice;
import TestGUI.model.exceptions.InvalidMoveException;
import TestGUI.model.utilities.CellObserver;

public interface Cell {
	Dice getDice();
	void move(Cell target) throws InvalidMoveException;
	void put(Dice dice) throws InvalidMoveException;
	boolean isEmpty();
	String asImage();
	void addObserver(CellObserver observer);
	void notifyObserver();
}