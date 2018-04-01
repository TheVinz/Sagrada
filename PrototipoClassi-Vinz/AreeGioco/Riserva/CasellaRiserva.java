package AreeGioco.Riserva;

public class CasellaRiserva implements Casella{
	private Dado dado;
	@Override
	public Dado getDado(){}
	@Override
	public void move(Casella target) throws MossaNonValidaException{}
	@Override
	public void put(Dado dado) throws MossaNonValidaException{}
}