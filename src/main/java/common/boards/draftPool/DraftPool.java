package common.boards.draftPool;
import common.bag.Bag;
import common.dice.Dice;

//Nel nostro programma la classe Riserva istanzierà un solo oggetto: Singleton? o altri pattern?

public class DraftPool{
	//Dimensione della riserva
	private final int size;
	private DraftPoolCell[] draftPoolSpaces;
	public DraftPool(int size){}
	public DraftPoolCell[] getDraftPool(){}
	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void drow(Bag sacco){}
	//inserisce un dado nella riserva nel sacchetto e ne pesca uno nuovo assegnandogli un valore scelto. Ritorna il dado pescato
	public Dice redrow(Bag sacco, Dice dice, int value){}
}