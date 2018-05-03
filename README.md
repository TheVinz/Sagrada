<<<<<<< HEAD
# ing-sw-2018-Santomarco-Scotti-Stucchi

=======================================================================================

Scotti:

	Ho tolto il pattern strategy
	Ho sistemato i package
	Ho fatto un esempio di interazione per la tool card
	Non ho idea di come il model aggiorni la view dopo la tool card

=======================================================================================

Vinz:

	Ho strutturato il programma in questo modo: 
		la RemoteView non fa altro che tradurre Command in oggetti dello stato 
		e oggetti dello stato in Changement, in modo tale da permettere 
		a server e client di lavorare in maniera completamente autonoma. 
		Il meccanismo di traduzione è nascosto dalle classi del package 
		remotemvc.
		Le toolcard funzionano bene o male come avevate gia visto voi, 
		non c'è più un ToolCardManager la cui funzione è stata divisa tra 
		Controller e ToolCard.
		Guardate un po' e vedete se capite un po', io vedo di commentare il possibile.

	Aggiunte alcune idee per la funzionalità di rete:
		Sia sul client che sul server ci sarà un piccolo sistema MVC che 
		permetterà ai due sistemi di lavorare in maniera indipendente
		dalla connessione.
		Per la gestione della rete tutto il codice verrà implementato 
		all' interno delle classi ViewProxy, ModelProxy e ControllerProxy,
		in modo che la gestione di changement e command sia totalmente 
		invisibile per Model, Controller e PlayerViewController.

	Aggiunte Tool Cards
		Testatele voi vedete se riuscite a capirne il funzionamento.
		Quelle che più mi preme vengano testate sono:
			-DiluentePerPastaSalda
			-PennelloPerPastaSalda
			-TaglierinaManuale
		
		NB Ho aggiunto dei TODO in giro, leggete e provate a implementare un po' di roba
========================================================================================

TEST:

	Tutte le objective cards sono testate.
	
	ToolCards testate:
		AlesatorePerLaminaDiRame
		DiluentePerPastaSalda
		Lathekin
		Martelletto
		PennelloPerEglomise
		PennelloPerPastaSalda
_________________________________________________________________________________________


Appena si può si passa al master.

>>>>>>> origin/biscotto
