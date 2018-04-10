package boards.draftpool;

public class DraftPoolSpace implements Space{
	private Dice dice;
	@Override
	public Dice getDice(){}
	@Override
	public void move(Space target) throws InvalidMoveException{}
	@Override
	public void put(Dice dice) throws InvalidMoveException{}
}