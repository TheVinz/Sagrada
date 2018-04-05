package bag;

//Esiste una sola istanza della classe: pattern??

public class Bag{
	//I char rappresentano i 5 colori dei dadi che sono posti nella pila in maniera casuale
	private Stack<Color> dices;
	//Creo la pila con 18 elementi per ogni char rappresentante i colori
	//NB dovrebbe esistere una funzione di libreria che mi fa mischiare la pila
	public Bag(){}
	//creo un nuovo dado del colore del char ottenuto pescando dalla pila
	public Dice drow(){}
	//Re inserisco un dado nel sacco
	public void insert(Dice dice){}
}