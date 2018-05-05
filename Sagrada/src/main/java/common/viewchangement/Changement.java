package common.viewchangement;

import client.view.ChangementVisitor;

public interface Changement {
	/*
	Il changement viene creato dalla RemoteView (server) mediante le classi dello stato e interpretato dal RemoteModel (client)
	in modo che la view non sappia che di mezzo sia successa questa traduzione.
	Il Changement è più complicato del Command (che si limita a notificare al controlle COSA ha selezionato il client), infatti
	il changement oltre a spiegare COSA è cambiato descrive anche COME, dunque dei semplici int qui non mi sono bastati per
	descrivere le varie tipologie di Changement.
	NB il meccanismo del ChangementType e getType può essere sostituito da un semplice getClass() nel RemoteModel. Secondo me
	però così il codice è più leggibile. 
	*/

    void change(ChangementVisitor changementVisitor) throws Exception;
}
