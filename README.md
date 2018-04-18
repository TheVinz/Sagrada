<<<<<<< HEAD
# ing-sw-2018-Santomarco-Scotti-Stucchi
=======
Scotti:

	Ho tolto il pattern strategy
	Ho sistemato i package
	Ho fatto un esempio di interazione per la tool card
	Non ho idea di come il model aggiorni la view dopo la tool card

Vinz:

	Ho implementato un po' di Changement e in più ho inserito la classe command, che comunica i comandi della view al controller.
	Da riguardare l' implementazione dei changement che danno problemi, in particolare per quanto riguarda il fatto che nel metodo
	notifyObservers(Changement change) viene inviata un' istanza di changement cosa che rende poi impossibile distinguere
	un changement dall' altro nel client.
	IN ALTERNATIVA
	Per ogni classe di changement che creiamo ci dovrà essere un apposito metodo di notifyObservers()
		(Per esempio: notifyMove(MoveChangement change){}, notifyRefill(RefillDraftPoolChangement change){})

	Aggiunte alcune idee per la funzionalità di rete:
		Sia sul client che sul server ci sarà un piccolo sistema MVC che permetterà ai due sistemi di lavorare in maniera indipendente
		dalla connessione.
		Per la gestione della rete tutto il codice verrà implementato all' interno delle classi ViewProxy, ModelProxy e ControllerProxy,
		in modo che la gestione di changement e command sia totalmente invisibile per Model, Controller e PlayerViewController.

>>>>>>> origin/biscotto
