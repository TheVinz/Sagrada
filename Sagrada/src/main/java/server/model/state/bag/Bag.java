package server.model.state.bag;

import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Bag class represents the container from which all the dices used in the game are taken.
 * The class Bag includes methods to draw a dice or to reinsert it.
 *
 */
public class Bag{

	private ArrayDeque<Color> dices;

	/**
	 * Initialize a newly created Bag so that it contains 18 dices for each of the 5 colors, with random values.
	 */

	public Bag(){
		int n=18;
		dices=new ArrayDeque<>();
		for(int i=0; i<n; i++){
			dices.push(Color.BLUE);
		}
		for(int i=0; i<n; i++){
			dices.push(Color.GREEN);
		}
		for(int i=0; i<n; i++){
			dices.push(Color.YELLOW);
		}
		for(int i=0; i<n; i++){
			dices.push(Color.PURPLE);
		}
		for(int i=0; i<n; i++){
			dices.push(Color.RED);
		}
		shuffle();
	}

	private void shuffle(){
		ArrayList<Color> list = new ArrayList<>(dices);
		Collections.shuffle(list);
		dices=new ArrayDeque<>(list);
	}

	/**
	 * Remove a random dice from the Bag.
	 *
	 * @return a random Dice
	 */
	public Dice draw() {
		return new Dice(dices.pop());
	}

	/**
	 * Remove a Dice with the specified value
	 *
	 * @param value of the Dice to return
	 * @return a Dice with a random color and the specified value
	 */
	public Dice draw(int value) {
		return new Dice(dices.pop(), value);
	}

	/**
<<<<<<< HEAD
	 * Insert a (@link server.model.state.dice.Dice) in the Bag.
	 *
	 * @param dice the dice that I want to reinsert in the Bag
=======
	 * Insert a {@link server.model.state.dice.Dice} in this Bag.
	 * @param dice the Dice that I want to reinsert in this Bag
>>>>>>> 4c176e2836ba4e15e4f405cb6445feeca79caf43
	 */
	public void insert(Dice dice){
		dices.push(dice.getColor());
		shuffle();
	}
}