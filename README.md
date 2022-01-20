# JobFinderApp
Lo scopo di questa applicazione è di permettere all'utente di trovare facilmente lavori riguardanti la programmazione in diverse città richieste proprio dall'utente. Inoltre, l'applicazione consente di calcolare delle statistiche sui lavori trovati e filtrare le richieste per diversi parametri legati al lavoro, quali: nome della compagnia, sorgente dell'annuncio, tipologia di contratto (part-time/full-time), data dell'annuncio, linguaggi richiesti e possibilità di lavorare da remoto.

## Indice:

 - [*Introduzione e diagrammi UML*](https://github.com/andreacaduceo/Progetto-OOP#introduzione-e-diagrammi)
 - [*Rotte disponibili*](https://github.com/andreacaduceo/Progetto-OOP#Rotte-Disponibili)
- [*Test*](https://github.com/andreacaduceo/Progetto-OOP#Test)
- [*Documentazione*](https://github.com/andreacaduceo/Progetto-OOP#Documentazione)


# Introduzione e diagrammi 
L'applicazione si basa sui dati forniti dalle API di [FindWork](https://findwork.dev/), è possibile consultare il seguente  [link](https://findwork.dev/developers/#api-key) per maggiori informazioni, come potersi registrare e ricevere la propria API key.
L'utente, dopo aver esportato il progetto, potrà lanciare il programma come SpringBoot App mediante il proprio IDE, una volta avviato verrà creato un server locale sulla porta 8080, mediante il protocollo HTTP, sul quale effettuare le varie chiamate disponibili. Inoltre, vista l'implementazione dei filtri, è possibile aggiungere facilmente nuove rotte che consentono ricerche con più parametri da filtrare.
Nella creazione del progetto l'utilizzo di diagrammi UML è stato di fondamentale aiuto, in quanto hanno permesso di gettare le fondamenta del progetto stesso, di seguito sono riportati i tre diagrammi utilizzati.

*`N.B.`* I seguenti diagrammi potrebbero non corrispondere esattamente al progetto finale, per via di modifiche in fase di sviluppo.

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
La rotta in questione è di tipo `GET`, essa non necessità di alcun *parametro* o *body* per funzionare.
 Il suo scopo è quello di suggerire all'utente cinque città per effettuare la ricerca, inoltre mostra anche il numero di lavori disponibili in tali città.
Ecco un esempio di questa chiamata:

![enter image description here](https://i.imgur.com/RiPzue4.png)

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

Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **IOException**: qualora si verifichino errori durante la lettura dei dati dalle API.

* **ParseException**: qualora ci siano problemi nel parsing de dati.

## /jobs

La rotta "/jobs" è di tipo `POST`,  per poter funzionare ha bisogno di ricevere un *body* contenente un JSONObject del tipo: 

    {
	     "Nomi delle città": "Prague,Madrid,London,Paris"
	}
    

 *`N.B.`* I nomi delle città da inserire nel JSONObject devono essere in inglese, poiché *FindWork* 
 non riconosce i nomi in italiano. 

Lo scopo di questa rotta è quello di permettere  all'utente di consultare i lavori disponibili nelle città richieste presenti nel JSONObject. Ecco un esempio di tale chiamata: 

![enter image description here](https://i.imgur.com/6MdIjqn.png)

A seguito di tale chiamata riceveremo un JSONArray di risposta come questo: 

![enter image description here](https://i.imgur.com/SMM1buQ.png)


Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **CityException**: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.

## /jobsByContract
La rotta "/jobsByContract" è di tipo `POST`, per un corretto funzionamento ha bisogno di ricevere un *body* come quello visto per la rotta precedente (`/jobs`) e di un *parametro* `contratto`, che può essere: `part time`o `full time`.
Lo scopo di questa rotta è di mostrare i lavori full-time/part-time presenti nelle città indicate nel JSONObject. Un esempio di questa chiamata è il seguente:

![enter image description here](https://i.imgur.com/2CgVr5o.png)

A seguito di una chiamata di questo tipo otterremo un JSONArray contenente solo i lavori full-time/part-time presenti nelle città del JSONObject, come ad esempio: 

![enter image description here](https://i.imgur.com/9obNg0k.png)

Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **CityException**: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.
 - **ParamException**: qualora non venga inserito il valore del parametro richiesto dalla chiamata, ad esempio `/chiamata?parametro= `.

## /jobsBySource 
La rotta `/jobsBySource`è una rotta di tipo `POST`, per un corretto funzionamento ha bisogno di ricevere un *body* come quello della rotta `/jobs ` e di un *parametro* `source`, relativo alla sorgente dell'annuncio.
Lo scopo di tale rotta è quello di mostrare i lavori disponibili nelle città indicate nel JSONObject, aventi come sorgente dell'annuncio quella indicata nel parametro. Un esempio di tale chiamata è il seguente: 

![enter image description here](https://i.imgur.com/lTpov28.png)

A seguito di una chiamata di questo tipo otterremo un JSONArray contenente solo i lavori con sorgente dell'annuncio indicata  ("Hn","Coroflot","Stackoverflow",ecc...)  presenti nelle città del JSONObject, come ad esempio:

![enter image description here](https://i.imgur.com/JdMo1N8.png)

Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **CityException**: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.
 - **ParamException**: qualora non venga inserito il valore del parametro richiesto dalla chiamata, ad esempio `/chiamata?parametro= `.

## /jobsByLanguage

La rotta `/jobsByLanguage` è di tipo `POST`, per un corretto funzionamento necessita di riceve un *body* come quello per la rotta `/jobs` e di un *parametro* `linguaggio`, relativo al linguaggio di programmazione che si desidera cercare.
Lo scopo di questa rotta è quindi quello di mostrare tutti i lavori contenenti il linguaggio di programmazione scelto nelle città indicate nel JSONObject, un esempio di tale chiamata è il seguente: 

![enter image description here](https://i.imgur.com/chD8Xzu.png)

A seguito di tale chiamata otterremo un JSONArray contenente solo i lavori nei quali è disponibile il linguaggio di programmazione desiderato ("php", "java", "react", ecc...) per le città indicate nel JSONObject, come ad esempio:

![enter image description here](https://i.imgur.com/xCiif1l.png)

Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **CityException**: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.
 - **ParamException**: qualora non venga inserito il valore del parametro richiesto dalla chiamata, ad esempio `/chiamata?parametro= `.

## /stats
La rotta `/stats` è una rotta di tipo `POST`, anch'essa per poter funzionare correttamente ha bisogno di ricevere un *body* come quello della rotta `/jobs` .
Lo scopo di tale rotta è mostrare all'utente delle statistiche relative ai lavori presenti nelle città indicate nel JSONObject, verranno quindi mostrate, per ogni città, informazioni relative a: lavori full-time e part-time, sia come quantità che percentuali, le sorgenti dei vari annunci, il numero di linguaggi disponibili e un elenco di tali linguaggi. Un esempio di tale chiamata è il seguente:

![enter image description here](https://i.imgur.com/oZP1Rtn.png)

A seguito di questa chiamata otterremo un JSONArray di risposta, come mostrato qui:

![enter image description here](https://i.imgur.com/8CLXvvK.png)

Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **CityException**: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.

## /statsBySource

La rotta `/statsBySource ` è una rotta di tipo `POST`,   affinché funzioni correttamente necessita di riceve un *body* contenente un JSONObject come per la rotta `/jobs`, e di un *parametro* `source`, relativo alla sorgente dell'annuncio desiderata.
Lo scopo di tale chiamata è quello di mostrare le statistiche, per ogni città del JSONObject, relative ai lavori aventi come sorgente quella immessa dall'utente. Un esempio di tale chiamata è il seguente: 

![enter image description here](https://i.imgur.com/e2h7h81.png)

A seguito di questa chiamata otterremo un JSONArray di riposta contenente le statistiche sui lavori aventi come sorgente dell'annuncio quella scelta dall'utente ("Hn","Coroflot","Stackoverflow",ecc...), ecco un esempio: 

![enter image description here](https://i.imgur.com/io0naAH.png)


Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **CityException**: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.
 - **ParamException**: qualora non venga inserito il valore del parametro richiesto dalla chiamata, ad esempio `/chiamata?parametro= `.

## /jobsByData

La rotta `/jobsByData` è una rotta di tipo `POST`, come per le altre necessita di un *body* contenente un JSONObject relativo alle città e di un *parametro* `data`, ad indicare il giorno dell'annuncio del lavoro, del formato **yyyy-mm-dd**.
Lo scopo di tale rotta è quello di calcolare statistiche per ogni città del JSONObject, sui lavori pubblicati nella data immessa dall'utente. Un esempio di tale chiamata è il seguente:

![enter image description here](https://i.imgur.com/8LVIzNE.png)

Da questa chiamata otterremo un JSONArray delle statistiche sui lavori filtrati simile a questo: 

![enter image description here](https://i.imgur.com/iTDVrqI.png)


Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **CityException**: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.
 - **ParamException**: qualora non venga inserito il valore del parametro richiesto dalla chiamata, ad esempio `/chiamata?parametro= `.
 - **DataException**: qualora il formato della data non rispecchia quello previsto **yyyy-mm-dd**.

## /statsByRemote

La rotta `/statsByRemote` è una rotta di tipo `POST`,  anche qui per un corretto funzionamento occorre passare come *body* un JSONObject contenete le città e un *parametro* `remoto`  relativo alla possibilità di lavoro da remoto. Tale parametro può essere di due tipi : *Disponibile* o *Non disponibile*.
Lo scopo di tale rotta è di mostrare all'utente le statistiche, per ogni città, relativi ai lavori con disponibilità, o meno, al lavoro da remoto. Un esempio di questa chiamata è il seguente: 

![enter image description here](https://i.imgur.com/25K9kd8.png)

La riposta di tale chiamate è un JSONArray delle statistiche come quello mostrato in figura qui sotto: 

![enter image description here](https://i.imgur.com/BMUxWPn.png)

Durante l'utilizzo di questa chiamata si possono verificare degli errori, qualora uno di essi si verifica varrà lanciata una delle seguenti *eccezioni*:

 - **CityException**: qualora il JSONObject nel body abbia valore nullo o una delle città inserite non è presente nel database di FindWork.
 - **ParamException**: qualora non venga inserito il valore del parametro richiesto dalla chiamata, ad esempio `/chiamata?parametro= `.

## Documentazione

L'applicazione è provvista anche di una [JavaDoc](https://github.com/andreacaduceo/Progetto-OOP/tree/main/JobFinderApp/javaDoc), con spiegazioni dettagliate dei metodi e delle classi usate all'interno del codice, qualora ci siano dubbi su delle parti del codice.

## Test
L'applicazione presenta anche due unità di test, consultabili [qui](https://github.com/andreacaduceo/Progetto-OOP/tree/main/JobFinderApp/src/test/java/com/project/JobFinderApp), per il corretto funzionamento di alcuni segmenti di codice. Qui segue una breve descrizione dei due test:

 - **JobInfoTest**: test relativo al corretto casting tramite il metodo `toJSONObject()` della classe *JobInfo*. Esso si occupa di verificare che un oggetto JobInfo castato in JSONObject tramite il metodo in questione sia uguale ad un effettivo JSONObject con le stesse coppie di chiave-valore.
 - **JobFinderAPITest**: test relativo alla corretta estrazione dei dati dalla response di *FindWork* tramite il metodo `estraiValori()` della classe *JobFinderAPI*. Il test in questione provvede a verificare che il JSONArray estratto dalla response di *FindWork* tramite il metodo citato sia uguale ad un nuovo JSONArray contenente lo stesso JSONObject.

##  Autori
*🟦* ***Caduceo Andrea***
*🟥* ***Quaglia Claudio***


