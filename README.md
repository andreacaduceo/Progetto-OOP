# JobFinderApp
Lo scopo di questa applicazione è di permettere all'utente di trovare facilmente lavori riguardanti la programmazione in diverse città richieste proprio dall'utente. Inoltre, l'applicazione consente di calcolare delle statistiche sui lavori trovati e filtrare le richieste per diversi parametri legati al lavoro, quali: nome della compagnia, sorgente dell'annuncio, tipologia di contratto (part-time/full-time), data dell'annuncio, linguaggi richiesti e possibilità di lavorare da remoto.

## Indice:

 - [*Introduzione e diagrammi UML*](https://github.com/andreacaduceo/Progetto-OOP#Introduzione%20e%20diagrammi%20UML)
 - [*Rotte disponibili*](https://github.com/andreacaduceo/Progetto-OOP#Rotte%20Disponibili)
- [*Test*](https://github.com/andreacaduceo/Progetto-OOP#Test)
- [*Documentazione*](https://github.com/andreacaduceo/Progetto-OOP#Documentazione)


# Introduzione e diagrammi 
L'applicazione si basa sui dati forniti dalle API di [FindWork](https://findwork.dev/), è possibile consultare il seguente  [link](https://findwork.dev/developers/#api-key) per maggiori informazioni, come potersi registrare e ricevere la propria API key.
L'utente, dopo aver esportato il progetto, potrà lanciare il programma come SpringBoot App mediante il proprio IDE, una volta avviato verrà creato un server locale sulla porta 8080, mediante il protocollo HTTP, sul quale effettuare le varie chiamate disponibili.
Nella creazione del progetto l'utilizzo di diagrammi UML è stato di fondamentale aiuto, in quanto hanno permesso di gettare le fondamenta del progetto stesso, di seguito sono riportati i tre diagrammi utilizzati.

`N.B.` I seguenti diagrammi potrebbero non corrispondere esattamente al progetto finale, per via di modifiche in fase di sviluppo.

 - **Diagramma dei casi d'uso**
 Il diagramma dei casi d'uso mostra le funzionalità che l'applicazione ha da offrire all'utente, fornendo una comprensione rapida all'utente di ciò che può fare.
 ![Diagramma dei casi d'uso](https://i.imgur.com/5fOO3So.png)
 
 - **Diagramma delle classi**
 Il diagramma delle classi ha lo scopo di descrivere tipi di entità, con le loro caratteristiche e le eventuali relazioni fra questi tipi.
 ![](https://i.imgur.com/XF2PVZl.png)
 - **Diagramma delle sequenze**
Il diagramma delle sequenze consente di descrivere una determinata sequenza di azioni, descrivendo le relazioni tra le varie entità del programma. Di seguito sono mostrati due diagrammi delle sequenze, uno relativo alla richiesta dei lavori, l'altro alla richiesta delle statistiche, entrambe le richieste presentano operazioni di filtraggio.
	- *Diagramma delle sequenze di una ricerca filtrata*
	
	![](https://i.imgur.com/BIYOAlt.png)
	
	
	- *Diagramma delle sequenze di una statistica filtrata*


![enter image description here](https://i.imgur.com/JYZDYs6.png)
	
	
 # Rotte disponibili
 Come detto in precedenza le chiamate devono essere effettuate al seguente indirizzo: `localhost:8080`, al quale verrà aggiunto poi uno dei seguenti path con relativi parametri, qualora siano necessari.

    
|TIPO DI RICHIESTA| ROTTA  |
|--|--|
|`GET` | /locationSuggestion|
|`POST` | /jobs
|`POST`|/jobsByContract|
|`POST`|/jobsBySource|
|`POST`|/jobsByLanguage|
|`POST`|/stats|
|`POST`|/statsBySource|
|`POST`|/statsByData|
|`POST`|/statsByRemote|

Di seguito una analisi dettagliata rotta per rotta.

## /locationSuggestion
La rotta in questione è di tipo `GET`, il suo scopo è quello di suggerire all'utente cinque città per effettuare la ricerca, inoltre mostra anche il numero di lavori disponibili in tali città.
Ecco un esempio di questa chiamata:

![enter image description here](https://i.imgur.com/2TKoSRo.png)

La cui risposta sarà un JSONObject simile a questo:

    {
	    "Numero di lavori in queste città": 131,
	    "Città suggerite": [
	        "London",
	        "Copenaghen",
	        "Madrid",
	        "Paris",
	        "Amsterdam"
	    ]
	}

*Eccezioni:

 - IOException: qualora si verifichino errori durante la lettura dei dati dalle API.

* ParseException: qualora ci siano problemi nel parsing de dati.

## /jobs

La rotta "/jobs" è di tipo `POST`,  per poter funzionare ha bisogno di ricevere un *body* contenente un JSONObject del tipo: 

    {
	     "Nomi delle città": "Prague,Madrid,London,Paris"
	}
Lo scopo di questa rotta è quello di permettere  all'utente di consultare i lavori disponibili nelle città richieste presenti nel JSONObject. Ecco un esempio di tale chiamata: 

![enter image description here](https://i.imgur.com/NiX77cZ.png)

A seguito di tale chiamata riceveremo un JSONArray di risposta come questo: 

![enter image description here](https://i.imgur.com/SMM1buQ.png)

*Eccezioni*

 - CityException: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.
