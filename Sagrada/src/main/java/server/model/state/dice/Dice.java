package server.model.state.dice;

import common.exceptions.InvalidMoveException;
import server.model.state.utilities.Color;

import java.util.Random;

/**
 * The Dice Class defines all the dices that can be draw from the bag.
 * The class Dice include methods to change the value of a dice.
 *
 */
public class Dice{
	private Color color;
	private int value;

	/**
	 * Initialize a newly dice with assigned {@link server.model.state.utilities.Color} and random value.
	 *
	 * @param color of the dice.
	 */

	public Dice(Color color){
		this.color=color;
		this.value=new Random().nextInt(6) +1;
	}

	/**
	 * Initialize a newly dice with assigned {@link server.model.state.utilities.Color} and random value.
	 *
	 * @param color of the dice.
	 * @param value of the dice.
	 */
	public Dice(Color color, int value){
		this.color=color;
		this.value=value;
	}

	/**
	 * Get the color of a dice
	 * @return {@link server.model.state.utilities.Color} of the dice
	 */
	public Color getColor(){
		return this.color;
	}

	/**
	 * Get the value of a dice
	 * @return value of the dice
	 */
	public int getValue(){
		return this.value;
	}

	/**
	 * Flip this dice to his opposite face.
	 */

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

	/**
	 * Increase the value of a dice by 1.
	 * @throws InvalidMoveException if the current value is six
	 */
	//Aumenta di 1 il valore
	public void increase() throws InvalidMoveException {
		if(value==6) throw new InvalidMoveException("Cannot increase 6");
		else value=value+1;
	}

	/**
	 * Decrease the value of a dice by 1.
	 * @throws InvalidMoveException if the current value is one
	 */
	//Diminuisce il valore del dado di 1
	public void decrease() throws InvalidMoveException {
		if(value==1) throw new InvalidMoveException("Cannot decrease 1");
		else value=value-1;
	}
}