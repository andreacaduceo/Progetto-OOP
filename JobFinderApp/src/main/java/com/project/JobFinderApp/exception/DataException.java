package com.project.JobFinderApp.exception;

/**
 * Classe che si occupa di segnalare errori relativi al formato di data inserito, nel caso in cui non sia del tipo:
 * [yyyy-mm-dd].
 */

public class DataException extends Exception {

    /**
     * Costruttore della classe DataException che permette di visualizzare il messaggio di errore, nel caso in cui venga
     * lanciata l'eccezione a essa relativa.
     */

    public DataException() {
        super("ERRORE: la data inserita non è nel formato corretto, provare con: yyyy-mm-dd");
    }

}
