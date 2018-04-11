package boards.draftpool;

//Nel nostro programma la classe Riserva istanzierà un solo oggetto: Singleton? o altri pattern?

public class DraftPool{
	//Dimensione della riserva
	private final int size;
	private DraftPoolSpace[] draftPoolSpaces;
	public DraftPool(int size){}
	public DraftPoolSpaces[] getDraftPool(){}
	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void drow(Bag sacco){}
	//inserisce un dado nella riserva nel sacchetto e ne pesca uno nuovo assegnandogli un valore scelto. Ritorna il dado pescato
	public Dado redrow(Bag sacco, Dice dice, int value){}
}