//Nel nostro programma la classe Riserva istanzierà un solo oggetto: Singleton? o altri pattern?

public class Riserva{
	//Dimensione della riserva
	private final int size;
	private CasellaRiserva[] dadiRiserva;
	public Riserva(){}
	public CasellaRiserva[] getRiserva(){}
	//Pesca nuovi dadi dal sacchetto e li aggiunge alla riserva
	public void drow(Sacco sacchetto){}
}