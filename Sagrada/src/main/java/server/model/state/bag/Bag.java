package server.model.state.bag;

import server.model.state.dice.Dice;
import server.model.state.utilities.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

//Esiste una sola istanza della classe: pattern??

public class Bag{
	//I char rappresentano i 5 colori dei dadi che sono posti nella pila in maniera casuale
	private ArrayDeque<Color> dices;
	//Creo la pila con 18 elementi per ogni char rappresentante i colori
	//NB dovrebbe esistere una funzione di libreria che mi fa mischiare la pila
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



	//creo un nuovo dado del colore del char ottenuto pescando dalla pila
	public Dice draw() {
		return new Dice(dices.pop());
	}

	public Dice draw(int value) {
		return new Dice(dices.pop(), value);
	}

	//Re inserisco un dado nel sacco
	public void insert(Dice dice){
		dices.push(dice.getColor());
		shuffle();
	}
}