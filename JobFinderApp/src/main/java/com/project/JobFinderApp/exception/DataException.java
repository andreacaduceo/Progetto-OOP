package com.project.JobFinderApp.exception;

public class DataException extends Exception {

    public DataException() {
        super("ERRORE: la data inserita non è nel formato corretto, provare con: yyyy-mm-dd");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
