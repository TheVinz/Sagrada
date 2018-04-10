package Dado;

public class Value{
	private int value;
	//Genera un numero casuale da 1 a 6
	public Value(){}
	//Costruttore privato richiamato solo dai metodi della classe
	private Value(int val){}
	//Restituisce un nuovo Value con valore aumentato di 1 (il vecchio valore non può essere 6)
	public Value increase() throws InvalidMoveException{}
	//Idem ma diminuisce e 1 non valido
	public Value decrease() throws InvalidMoveException{}
	//Mossa ottenuta girando il dado a faccia in giù
	public Value flip(){}
}