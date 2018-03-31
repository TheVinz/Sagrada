public class CasellaRiserva implements Posizione{
	private Dado dado;
	@Override
	public Dado getDado(){}
	@Override
	public void move(Posizione target) throws MossaNonValidaException{}
	@Override
	public void put(Dado dado) throws MossaNonValidaException{}
}