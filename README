PROTOTIPO CLASSI VINZ

Guardate e vedete un po' se vi sembra logico o meno oppure se ho dimenticato qualche pezzo.
Mancano le classi che descrivono gli effetti di ogni carta strumento e ogni carta obiettivo, le implementeremo poi. Per il momento penso l' interfaccia descritta si aabbastanza esplicativa. In seguito vorremo avere una classe per ogni carta, il problema sarà rendere acessibile l' istanza di ogni carta a tutti i giocatori (usando qualche pattern o alla peggio inserire nella classe Partita 2 array: uno per tutte le carte strumento e uno per le carte obiettivo pubblico in gioco).

Inoltre fate caso alle strutture dati che ho usato, in particolare la pila per gestire il sacco e l' array di set per la tabella dei round.
Per il sacco ho pensato di implementare una pila di char [r,g,v,b,p] (p sta per porpora, ndr) dove ogni char è ripetuto 18 volte, dopodichè la pila implementata in java dovrebbe avere un metodo che mischia il tutto. A questo punto "pescare un dado" si tradurrebbe come una semplice pop() dallo stack e successiva creazione di un dado con il colore ottenuto.
Per la tabella dei round poichè ho pensato di gestire i dadi piazzati mediante degli oggetti "Casella" per gestire la possibilità che in una casella dei round possono esserci più dadi ho pensato ad inserire un array di Set<Casella>. Vedete voi.

Le classi presenti nel package Partita sono fatte abbastanza a caso perchè penso saranno le ultime a essere messe a posto in quanto ritengo dipendano fortemente dall' implementazione di tutte le atre classi.

Facetemi sapere che ne pensate.
