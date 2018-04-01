package AreeGioco.Vetrata;

public class CasellaVetrata implements Casella{
	//Dado presente nella casella
	private Dado dado;
	private Color colore;
	private int sfumatura;
	@Override
	public Dado getDado(){}
	@Override
	public void move(Casella target) throws MossaNonValidaException{}
	@Override
	public void put(Dado dado) throws MossaNonValidaException{}
}