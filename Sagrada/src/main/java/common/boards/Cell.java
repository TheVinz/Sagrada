package common.boards;
import common.dice.Dice;
import common.exceptions.InvalidMoveException;

public interface Cell {
	Dice getDice();
	void move(Cell target) throws InvalidMoveException;
	void put(Dice dice) throws InvalidMoveException;
	boolean isEmpty();
}