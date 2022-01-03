package com.project.JobFinderApp.model;

import org.json.simple.JSONObject;

import java.util.Vector;

public class JobInfo {

    private String nomeCompagnia;
    private String tipoContratto;
    private String location;
    private String dataAnnuncio;
    private String source;
    private Vector<String > linguaggi;

    public JobInfo(String nomeCompagnia, String tipoContratto, String location, String dataAnnuncio, String source, Vector<String> linguaggi){
        this.nomeCompagnia = nomeCompagnia;
        this.tipoContratto = tipoContratto;
        this.location = location;
        this.dataAnnuncio = dataAnnuncio;
        this.source = source;
        this.linguaggi = linguaggi;
    }

    public String getNomeCompagnia() {
        return nomeCompagnia;
    }

    public void setNomeCompagnia(String nomeCompagnia) {
        this.nomeCompagnia = nomeCompagnia;
    }

    public String getTipoContratto() {
        return tipoContratto;
    }

    public void setTipoContratto(String tipoContratto) {
        this.tipoContratto = tipoContratto;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDataAnnuncio() {
        return dataAnnuncio;
    }

    public void setDataAnnuncio(String dataAnnuncio) {
        this.dataAnnuncio = dataAnnuncio;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public Vector<String> getLinguaggi() {
        return linguaggi;
    }

    public void setLinguaggi(Vector<String> linguaggi) {
        this.linguaggi = linguaggi;
    }

    public JSONObject toJSONObject () {
        JSONObject jobInfoObj = new JSONObject();
        jobInfoObj.put("Nome della compagnia",nomeCompagnia);
        jobInfoObj.put("Tipo di contratto", tipoContratto);
        jobInfoObj.put("Località", location);
        jobInfoObj.put("Data anuncio", dataAnnuncio);
        jobInfoObj.put("Source", source);
        jobInfoObj.put("Linguaggi", linguaggi);

        return jobInfoObj;
    }
}
