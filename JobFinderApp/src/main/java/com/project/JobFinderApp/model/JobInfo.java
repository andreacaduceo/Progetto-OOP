package com.project.JobFinderApp.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JobInfo {

    private String nomeCompagnia;
    private String tipoContratto;
    private String location;
    private String dataAnnuncio;
    private String source;
    private String remoto;
    private JSONArray linguaggi;




    public JobInfo(String nomeCompagnia, String tipoContratto, String location, String dataAnnuncio, String source, String remoto, JSONArray linguaggi){
        this.nomeCompagnia = nomeCompagnia;
        this.tipoContratto = tipoContratto;
        this.location = location;
        this.dataAnnuncio = dataAnnuncio;
        this.source = source;
        this.remoto = remoto;
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

    public String getRemoto() { return remoto; }

    public void setRemoto(String remoto) { this.remoto = remoto; }

    public JSONArray getLinguaggi() {
        return linguaggi;
    }

    public void setLinguaggi(JSONArray linguaggi) {
        this.linguaggi = linguaggi;
    }

    public JSONObject toJSONObject () {
        JSONObject jobInfoObj = new JSONObject();
        jobInfoObj.put("Nome della compagnia",nomeCompagnia);
        jobInfoObj.put("Tipo di contratto", tipoContratto);
        jobInfoObj.put("Località", location);
        jobInfoObj.put("Data annuncio", dataAnnuncio);
        jobInfoObj.put("Source", source);
        jobInfoObj.put("Possibilità di lavoro da remoto", remoto);
        jobInfoObj.put("Linguaggi", linguaggi);

        return jobInfoObj;
    }
}
