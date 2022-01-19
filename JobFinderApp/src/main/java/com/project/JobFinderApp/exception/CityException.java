package com.project.JobFinderApp.exception;

/**
 * Classe che si occupa di segnalare l'eccezione generata a seguito di un mancato inserimento di citta' o di citta' non
 * presenti nel database di FindWork.
 */

public class CityException extends Exception {

    /**
     * Costruttore della classe CityException che permette di visualizzare il messaggio di errore qualora si verifichino
     * le condizioni dell'eccezione.
     */

    public CityException(){
        super("ERRORE: nomi delle città assenti o errati, controllare e riprovare.");
    }

}
