package AreeGioco.Riserva;

//Nel nostro programma la classe Riserva istanzierà un solo oggetto: Singleton? o altri pattern?

public class Riserva{
	//Dimensione della riserva
	private final int size;
	private CasellaRiserva[] dadiRiserva;
	public Riserva(){}
	public CasellaRiserva[] getRiserva(){}
	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void drow(Sacco sacchetto){}
	//inserisce un dado nella riserva nel sacchetto e ne pesca uno nuovo assegnandogli un valore scelto. Ritorna il dado pescato
	public Dado redrow(Sacco sacchetto, Dado dado, int val){}
}