package server.model.state.dice;

import common.exceptions.InvalidMoveException;
import server.model.state.utilities.Color;

import java.util.Random;

public class Dice{
	private Color color;
	private int value;
	//Crea un nuovo dado con un valore casuale
	public Dice(Color color){
		this.color=color;
		this.value=new Random().nextInt(6) +1;
	}
	//Crea un nuovo dado con un colore e un valore predefiniti
	public Dice(Color color, int value){
		this.color=color;
		this.value=value;
	}
	public Color getColor(){
		return this.color;
	}
	public int getValue(){
		return this.value;
	}
	//gira il dado a faccia in giù
	public void flip(){
		switch(value){
			case 1 :
				value=6;
				break;
			case 2:
				value=5;
				break;
			case 3:
				value=4;
				break;
			case 4:
				value=3;
				break;
			case 5:
				value=2;
				break;
			case 6:
				value=1;
				break;
			default:
				break;
		}
	}
	//Aumenta di 1 il valore
	public void increase() throws InvalidMoveException {
		if(value==6) throw new InvalidMoveException("Cannot increase 6");
		else value=value+1;
	}
	//Diminuisce il valore del dado di 1
	public void decrease() throws InvalidMoveException {
		if(value==1) throw new InvalidMoveException("Cannot decrease 1");
		else value=value-1;
	}
}