package dice;

public class Dice{
	Color color;
	Value value;
	//Crea un nuovo dado con un valore casuale
	public Dice(Color color){}
	//Crea un nuovo dado con un colore e un valore predefiniti
	public Dice(Color color, Value value){}
	public Color getColor(){}
	public Value getValue(){}
	//gira il dado a faccia in giù
	public void flip(){}
	//Aumenta di 1 il valore
	public void increse() throws InvalidMoveException {}
	//Diminuisce il valore del dado di 1
	public void decrease() throws InvalidMoveException {}
}