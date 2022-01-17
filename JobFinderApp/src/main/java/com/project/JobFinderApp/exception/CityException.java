package com.project.JobFinderApp.exception;

public class CityException extends Exception {

    public CityException(){
        super("ERRORE: nomi delle città assenti o errati, controllare e riprovare.");
    }

}
