package boards.roundtrack;

//Una sola istanza per la classe

public class RoundTrack{
	//Ogni casella sul tracciato dei round è rappresentato come un set di caselle poichè per ogni round posso avere più dadi
	private Set<RoundTrackSpace>[] roundTrackSpaces;
	private final int size=10;
	//Round attuale
	private int round;
	public RoundTrack(){}
	//Aumenta il round
	public void newRound(){}
	//Aggiunge un dado al set di dadi del turno attuale
	public void add(Dice dice){}
	//Ritorna il set di dadi del turno indicato
	public Set<RoundTrackRace> getSet(int pos){}
	//Ritorna un array dei colori dei dadi presenti sulla tabella, può tornare utile per l' effetto di alcune toolcards
	public List<Color> getColors(){}
	public int getRound(){}
}