package Dado;

public class Dado{
	Color colore;
	Value valore;
	//La posizione può essere sul tracciato dei round, nella riserva oppure sulla casella di una vetrata
	Posizione pos;
	//Crea un nuovo dado con un valore casuale
	public Dado(Color colore){}
	//Crea un nuovo dado con un colore e un valore predefiniti
	public Dado(Color colore, Value valore){}
	public Color getColor(){}
	public Value getValue(){}
	public Posizione getPos(){}
	//gira il dado a faccia in giù
	public void flip(){}
	//Aumenta di 1 il valore
	public void increse() throws MossaNonValidaException {}
	//Diminuisce il valore del dado di 1
	public void decrease() throws MossaNonValidaException {}
	//Sposta il dado in una nuova posizione
	public void move() throws MossaNonValidaException {}
}