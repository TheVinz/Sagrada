package common.boards;
import common.dice.Dice;

public interface Cell {
	Dice getDice();
	void move(Cell target) throws InvalidMoveException;
	void put(Dice dice) throws InvalidMoveException;
}