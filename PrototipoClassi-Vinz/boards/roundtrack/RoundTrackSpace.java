package boards.roundtrack;

public class RoundTrackSpace implements Space{
	private Dice dice;
	@Override
	public Dice getDice(){}
	//Ritorna il colore del dado, può tornare utile per l' effetto di alcune toolcards
	public Color getColor(){}
	@Override
	public void move(Space target) throws InvalidMoveException{}
	@Override
	public void put(Dice dice) throws InvalidMoveException{}
}