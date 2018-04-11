package common.boards.draftPool;
import common.dice.Dice;
import common.boards.Cell;

public class DraftPoolCell implements Cell{
	private Dice dice;
	@Override
	public Dice getDice(){}
	@Override
	public void move(Cell target) throws InvalidMoveException{}
	@Override
	public void put(Dice dice) throws InvalidMoveException{}
}