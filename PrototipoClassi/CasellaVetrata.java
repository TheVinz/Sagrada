public class CasellaVetrata implements Posizione{
	//Dado presente nella casella
	private Dado dado;
	private Color colore;
	private int sfumatura;
	@Override
	public Dado getDado(){}
	@Override
	public void move(Posizione target) throws MossaNonValidaException{}
	@Override
	public void put(Dado dado) throws MossaNonValidaException{}
}