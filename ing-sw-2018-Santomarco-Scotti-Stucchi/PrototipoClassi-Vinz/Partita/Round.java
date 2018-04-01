package Partita;

public class Round{
	private Player giocatoreAttivo;
	//Giocatore che ha iniziato il round
	private Player primoGiocatore;
	//Nel costruttore viene gestita la routine di inizio round
	public Round(){}
	//Serie di azioni svolte al termine del round
	public void fineRound(){}
	//Serie di azioni svolte dal giocatore attivo, il metodo non solleva eccezioni poichè eventuali eccezioni
	//lanciate dai metodi riguardanti i dadi e le caselle di gioco vengono gestite qua dentro
	public void doSomething(){}
	//Aggiorna giocatore attivo e determina se il round è concluso o meno
	public void nextPlayer(){}
}