package Sacco;

//Esiste una sola istanza della classe: pattern??

public class Sacco{
	//I char rappresentano i 5 colori dei dadi che sono posti nella pila in maniera casuale
	private Stack<char> dadi;
	//Creo la pila con 18 elementi per ogni char rappresentante i colori
	//NB dovrebbe esistere una funzione di libreria che mi fa mischiare la pila
	public Sacco(){}
	//creo un nuovo dado del colore del char ottenuto pescando dalla pila
	public Dado drow(){}
	//Re inserisco un dado nel sacco
	public void insert(Dado dado){}
}