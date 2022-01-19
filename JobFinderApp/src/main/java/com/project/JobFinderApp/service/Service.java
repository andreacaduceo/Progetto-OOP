package com.project.JobFinderApp.service;

import com.project.JobFinderApp.exception.DataException;
import com.project.JobFinderApp.exception.CityException;
import com.project.JobFinderApp.exception.ParamException;
import com.project.JobFinderApp.util.JobFinderAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Classe contenente i metodi richiamati dal Controller, essa rappresenta il servizio su cui si basa l'applicazione.
 */

@Component
public class Service {

    private JobFinderAPI api = new JobFinderAPI();

    /**
     * Questo metodo restituisce le informazioni dei lavori per date citta', dopo aver interrogato FindWork e aver
     * estrapolato le informazioni d'interesse.
     *
     * @param nomiCitta JSONObject contenente le citta' inserite dall'utente.
     * @return JSONArray con informazioni relative ai lavori presenti nelle citta' scelte.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray chiamataPiuLocation(JSONObject nomiCitta) throws CityException {
        return api.estraiValori(api.getMoreLocations(nomiCitta));
    }

    /**
     * Questo metodo restituisce all'utente un set preimpostato di cinque citta', con il numero totale dei lavori presenti
     * al momento in tali citta'.
     *
     * @return JSONObject contenente i nomi delle citta' suggerite e il numero di lavori totali.
     * @throws IOException Eccezione che viene lanciata qualora ci siano problemi d'input/output.
     * @throws ParseException Eccezione lanciata qualora ci siano problemi con il parsing della response.
     */

    public JSONObject suggerisciLocation() throws IOException, ParseException {
        return api.suggerisciLocation();
    }

    /**
     * Questo metodo restituisce le informazioni dei lavori nelle citta' scelte, filtrate per tipologia di contratto.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param contratto Stringa relativa alla tipologia di contratto (part time/full time).
     * @return JSONArray con informazioni relative ai lavori, filtrati per tipo di contratto, nelle citta' inserite.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray chiamataFiltrataPerContratto(JSONObject citta, String contratto) throws ParamException, CityException {
        return api.filteredByContract(citta, contratto);
    }

    /**
     * Questo metodo restituisce le informazioni dei lavori nelle citta' scelte, filtrate per sorgente d'annuncio.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param source Stringa contenente la sorgente desiderata dell'annuncio.
     * @return JSONArray con informazioni relative ai lavori, filtrati per sorgente, delle citta' scelte.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray chiamataFiltrataPerSource(JSONObject citta, String source) throws ParamException, CityException {
        return api.filteredBySource(citta, source);
    }

    /**
     * Questo metodo restituisce le informazioni dei lavori nelle citta' scelte, filtrate per linguaggio di programmazione.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param linguaggio Stringa contenente il linguaggio desiderato.
     * @return JSONArray con informazioni relative ai lavori, filtrati per linguaggio di programmazione, delle citta' scelte.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray chiamataFiltrataPerLinguaggio(JSONObject citta, String linguaggio) throws ParamException, CityException {
        return api.filteredByLanguage(citta, linguaggio);
    }

    /**
     * Questo metodo restituisce delle statistiche generali per ogni citta' scelta dall'utente.
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @return JSONArray contenente le statistiche generali dei lavori divise per citta'.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheGenerali(JSONObject citta) throws CityException {
        return api.statisticheGenerali(citta);
    }

    /**
     * Questo metodo restituisce statistiche filtrate per sorgente dell'annuncio per ogni citta' scelta dall'utente.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param source Stringa relativa alla sorgente d'interesse dell'utente.
     * @return JSONArray contenente le statistiche dei lavori, filtrati per sorgente, divise per citta'.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheSource(JSONObject citta, String source) throws ParamException, CityException {
        return api.statisticheFiltratePerSource(citta, source);
    }

    /**
     * Questo metodo restituisce statistiche filtrate per data dell'annuncio per ogni citta' scelta dall'utente.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param data Stringa relativa alla data dell'annuncio dei lavori.
     * @return JSONArray contenente le statistiche dei lavori, filtrati per data di annuncio, divise per citta'.
     * @throws DataException Eccezione lanciata qualora il formato della data non sia: yyyy-mm-dd.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheData(JSONObject citta, String data) throws DataException, ParamException, CityException {
        return api.statisticheFiltratePerData(citta, data);
    }

    /**
     * Questo metodo restituisce statistiche filtrate per possibilita' di lavoro da remoto per ogni citta' scelta dall'utente.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param remoto Stringa relativa alla disponibilita' o meno di lavoro da remoto.
     * @return JSONArray contenente le statistiche dei lavori, filtrati per disponibilita' al lavoro da remoto, divise per citta'.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheRemoto(JSONObject citta, String remoto) throws ParamException, CityException {
        return api.statisticheFiltratePerRemoto(citta, remoto);
    }
}
