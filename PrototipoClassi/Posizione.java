public interface Posizione{
	private Dado dado;
	public Dado getDado(){}
	public void move(Posizione target) throws MossaNonValidaException{}
	public void put(Dado dado) throws MossaNonValidaException{}
}