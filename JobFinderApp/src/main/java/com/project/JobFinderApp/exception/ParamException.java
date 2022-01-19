package com.project.JobFinderApp.exception;

/**
 * Classe che si occupa di segnalare eccezioni presenti all'inserimento del parametro. La relativa eccezione viene lanciata
 * se il parametro inserito e' vuoto: ( ?parametro=  ).
 */

public class ParamException extends Exception {

    /**
     * Costruttore della classe ParamException che permette di visualizzare il messaggio di errore relativo all'eccezione
     * lanciata.
     * @param parametro Stringa relativa al nome del parametro lasciato vuoto.
     */

    public ParamException(String parametro) {
        super("ERRORE: il parametro " + parametro + " è vuoto. Inserirne uno valido.");
    }

}
