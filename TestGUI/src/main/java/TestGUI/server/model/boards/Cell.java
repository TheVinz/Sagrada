package TestGUI.server.model.boards;

import TestGUI.server.model.dice.Dice;
import TestGUI.server.model.exceptions.InvalidMoveException;

public interface Cell {
	Dice getDice();
	void move(Cell target) throws InvalidMoveException;
	void put(Dice dice) throws InvalidMoveException;
	boolean isEmpty();
	String asImage();
}