package com.project.JobFinderApp.exception;

public class ParamException extends Exception {

    public ParamException() {
        super("ERRORE: il parametro è vuoto. Inserirne uno valido.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
