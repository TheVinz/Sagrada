ROBA DI VINZ, NON TOCCARE ALTRIMENTI GIT IMPAZZISCE.

Prova implementazione Model-View-Controller, guardare e dire se piace il lavoro o meno.

Se stai leggendo il readme (wow!!! leggi i readme!!!) dimmelo che così ci scrivo cose utili anzichè stronzate. Scotti gay.

SULLE CLASSI

	Classi sul server:
		Model
		GameController
		State
		Player
		
	Classi sul client:
		View
		PlayerController
		

METODI CHIAMATI DA CLASSI AL DI FUORI DI QUESTA CARTELLA

	ToolCard.java
	public static ToolCard[] getToolCards()
		metodo static che ritorna un array di tool cards che saranno quelle poi messe a 
		disposizione dei giocatori durante la partita

	ObjectiveCard.java
	public static PublicObjectiveCard[] getPublicObjectiveCards()
		metodo static che ritorna un array di bla bla bla come sopra

	ObjectiveCard.java
	public static PrivateObjectiveCard getPrivateObjectiveCard()
		metodo bla bla bla che ritorna una singola carta obiettivo privato per il singolo player

	DraftPool.java
	public void drow() 
		pesca una nuova draft pool dal sacco
	public String toString()
		ritorna una rappresentazione della draft pool da stampare a schermo nella view
	public Dice get(int dice)
		ritorna il dado nella draft pool corrispondente all' indice indicato
		NB L'indice corrisponde a quello indicato mediante il metodo DraftPool.toString()

	WindowFrame.java
	public WindowFrameSpace get(int i, int j)
		ritorna il window frame space corrispondente alle coordinate indicate

	Dice.java
	public void move(Space target) throws invalid move exception
		prova a muovere il dado nella posizione target, se la mossa non è valida solleva un' eccezione
