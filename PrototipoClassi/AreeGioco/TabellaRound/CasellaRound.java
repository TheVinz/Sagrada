package AreeGioco.TabellaRound;

public class CasellaRound implements Casella{
	private Dado dado;
	@Override
	public Dado getDado(){}
	//Ritorna il colore del dado, può tornare utile per l' effetto di alcune toolcards
	public Color getColore(){}
	@Override
	public void move(Casella target) throws MossaNonValidaException{}
	@Override
	public void put(Dado dado) throws MossaNonValidaException{}
}