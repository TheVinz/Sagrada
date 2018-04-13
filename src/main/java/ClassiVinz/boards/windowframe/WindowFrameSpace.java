package spaces.windowframe;

public class WindowFrameSpace implements Space{
	//Dado presente nella casella
	private Dice dice;
	private Color color;
	private int shade;
	@Override
	public Dice getDice(){}
	@Override
	public void move(Space target) throws InvalidMoveException{}
	@Override
	public void put(Dice dice) throws InvalidMoveException{}
}
