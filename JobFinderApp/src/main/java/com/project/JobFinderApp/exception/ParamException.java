package com.project.JobFinderApp.exception;

public class ParamException extends Exception {

    public ParamException(String parametro) {
        super("ERRORE: il parametro " + parametro + " è vuoto. Inserirne uno valido.");
    }

}
