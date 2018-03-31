//Una sola istanza per la classe

public class TabellaRound{
	//Ogni casella sul tracciato dei round è rappresentato come un set di caselle poichè per ogni round posso avere più dadi
	private Set<CasellaRound>[] dadiRound;
	private final int size=10;
	//Round attuale
	private int round;
	public TabellaRound(){}
	//Aumenta il round
	public void newRound(){}
	//Aggiunge un dado al set di dadi del turno attuale
	public void add(Dado dado){}
	//Ritorna il set di dadi del turno indicato
	public Set<CasellaRound> getSet(int i){}
}