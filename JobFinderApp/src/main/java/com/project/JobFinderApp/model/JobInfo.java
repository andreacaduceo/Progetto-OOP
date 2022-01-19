package com.project.JobFinderApp.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe che modella un oggetto di tipo JobInfo nel quale vengono raccolte le informazioni relative ai lavori prese
 * da FindWork, come: nome della compagnia, tipologia di contratto, citta', data dell'annuncio, sorgente, possibilità di
 * lavoro da remoto e linguaggi di programmazione disponibili.
 */

public class JobInfo {

    private String nomeCompagnia;
    private String tipoContratto;
    private String location;
    private String dataAnnuncio;
    private String source;
    private String remoto;
    private JSONArray linguaggi;


    /**
     * Costruttore della classe JobInfo che assegna i valori dalla response di FindWork alle variabili d'istanza.
     *
     * @param nomeCompagnia Nome della compagnia che offre il lavoro.
     * @param tipoContratto Tipologia di contratto (part time/full time).
     * @param location Citta' alla quale fanno riferimento le informazioni.
     * @param dataAnnuncio Data dell'annuncio del lavoro.
     * @param source Sorgente dell'annuncio.
     * @param remoto Possibilità di lavoro da remoto.
     * @param linguaggi Linguaggi di programmazione disponibili per il lavoro.
     */

    public JobInfo(String nomeCompagnia, String tipoContratto, String location, String dataAnnuncio, String source, String remoto, JSONArray linguaggi) {
        this.nomeCompagnia = nomeCompagnia;
        this.tipoContratto = tipoContratto;
        this.location = location;
        this.dataAnnuncio = dataAnnuncio;
        this.source = source;
        this.remoto = remoto;
        this.linguaggi = linguaggi;
    }

    /**
     * Metodo get per la variabile nomeCompagnia.
     *
     * @return Stringa contenente il nome della compagnia.
     */

    public String getNomeCompagnia() {
        return nomeCompagnia;
    }

    /**
     * Metodo set per la variabile nomeCompagnia.
     *
     * @param nomeCompagnia Il nome della compagnia che offre il lavoro.
     */

    public void setNomeCompagnia(String nomeCompagnia) {
        this.nomeCompagnia = nomeCompagnia;
    }

    /**
     * Metodo get per la variabile tipoContratto.
     *
     * @return Stringa contenente la tipologia di contratto offerta.
     */

    public String getTipoContratto() {
        return tipoContratto;
    }

    /**
     * Metodo set per la variabile tipoContratto.
     *
     * @param tipoContratto La tipologia di contratto del lavoro.
     */

    public void setTipoContratto(String tipoContratto) {
        this.tipoContratto = tipoContratto;
    }

    /**
     * Metodo get per la variabile location.
     *
     * @return Stringa contenente la location del lavoro offerto.
     */

    public String getLocation() {
        return location;
    }

    /**
     * Metodo set per la variabile location.
     *
     * @param location La citta' relativa al lavoro considerato.
     */

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Metodo get per la variabile dataAnnuncio.
     *
     * @return Stringa contenente la data dell'annuncio del lavoro.
     */

    public String getDataAnnuncio() {
        return dataAnnuncio;
    }

    /**
     * Metodo set per la variabile dataAnnuncio.
     *
     * @param dataAnnuncio La data dell'annuncio.
     */

    public void setDataAnnuncio(String dataAnnuncio) {
        this.dataAnnuncio = dataAnnuncio;
    }

    /**
     * Metodo get per la variabile source.
     *
     * @return Stringa contenente la sorgente dell'annuncio.
     */

    public String getSource() {
        return source;
    }

    /**
     * Metodo ser per la variabile source.
     *
     * @param source La sorgente dell'annuncio.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Metodo get per la variabile remoto.
     *
     * @return Stringa contenente la disponibilita' al lavoro da remoto.
     */

    public String getRemoto() {
        return remoto;
    }

    /**
     * Metodo ser per la variabile remoto.
     *
     * @param remoto La possibilita' di lavoro da remoto (Disponibile/Non disponibile).
     */

    public void setRemoto(String remoto) {
        this.remoto = remoto;
    }

    /**
     * Metodo get per la variabile linguaggi.
     *
     * @return JSONArray contenente i linguaggi disponibili per il lavoro in questione.
     */

    public JSONArray getLinguaggi() {
        return linguaggi;
    }

    /**
     * Metodo set per la variabile linguaggi.
     *
     * @param linguaggi Linguaggi disponibili per tale lavoro.
     */
    public void setLinguaggi(JSONArray linguaggi) {
        this.linguaggi = linguaggi;
    }

    /**
     * Metodo che si occupa di convertire in JSONObject un oggetto di tipo JobInfo.
     *
     * @return JSONObject contente i parametri d'interesse del lavoro.
     */
    public JSONObject toJSONObject() {
        JSONObject jobInfoObj = new JSONObject();
        jobInfoObj.put("Nome della compagnia", nomeCompagnia);
        jobInfoObj.put("Tipo di contratto", tipoContratto);
        jobInfoObj.put("Località", location);
        jobInfoObj.put("Data annuncio", dataAnnuncio);
        jobInfoObj.put("Source", source);
        jobInfoObj.put("Lavoro da remoto", remoto);
        jobInfoObj.put("Linguaggi", linguaggi);
        return jobInfoObj;
    }
}
