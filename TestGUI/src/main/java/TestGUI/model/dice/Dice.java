package TestGUI.model.dice;


import TestGUI.model.exceptions.InvalidMoveException;
import javafx.scene.paint.Color;


import java.util.Random;

import static javafx.scene.paint.Color.*;

public class Dice{
	private Color color;
	private int value;
	//Crea un nuovo dado con un valore casuale
	public Dice(Color color){
		this.color=color;
		this.value=new Random().nextInt(5) +1;
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

	public String asImage(){
		String image;
		image = "File:resources/icons/";
		if(color==BLUE) image=image+"blue";
		else if(color==RED) image=image+"red";
		else if(color==PURPLE) image=image+"purple";
		else if(color==GREEN) image=image + "green";
		else if(color==YELLOW) image=image+"yellow";
		else image="";
		switch(this.value){
			case 1:
				return image + "one.png";
			case 2:
				return image + "two.png";
			case 3:
				return image + "three.png";
			case 4:
				return image + "four.png";
			case 5:
				return image + "five.png";
			case 6:
				return image + "six.png";
			default:
				return null;
		}
	}
}