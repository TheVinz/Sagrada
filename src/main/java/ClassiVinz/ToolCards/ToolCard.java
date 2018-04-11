package ToolCards;

//Ogni classe che implementerà questa interfaccia sarà una di quelle nel PDF ToolCards

public class ToolCard{ //enum??
	//Ho inserito il parametro playerView in modo tale che la tool card possa richiamare il metodo print della view per stampare a schermo 
	//la richiesta dei parametri e richiamare il metodo getInput in modo da avere input da tastiera da parte della view
	//Il metodo getInput della view è scritto in modo tale che la tool card richieda parametri di tipo int.
	//Il riferimento al model serve per poter trasformare l' input del giocatore in oggetti.
	public doSomething(Model model, View playerView) throws MossaNonValidaException{};
}