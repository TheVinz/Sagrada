package boards;

public interface Space{
	public Dado getDice();
	public void move(Space target) throws InvalidMoveException;
	public void put(Dice dice) throws InvalidMoveException;
}