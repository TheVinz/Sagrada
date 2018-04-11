package common.dice;
import common.utilities.Color;

public class Dice{
	Color color;
	int value;
	//Crea un nuovo dado con un valore casuale
	public Dice(Color color){}
	//Crea un nuovo dado con un colore e un valore predefiniti
	public Dice(Color color, int value){}
	public Color getColor(){}
	public int getValue(){}
	//gira il dado a faccia in giù
	public void flip(){}
	//Aumenta di 1 il valore
	public void increse() throws InvalidMoveException {}
	//Diminuisce il valore del dado di 1
	public void decrease() throws InvalidMoveException {}
}