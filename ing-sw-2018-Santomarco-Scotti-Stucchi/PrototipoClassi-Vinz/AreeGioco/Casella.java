package AreeGioco;

public interface Casella{
	public Dado getDado(){}
	public void move(Casella target) throws MossaNonValidaException{}
	public void put(Dado dado) throws MossaNonValidaException{}
}