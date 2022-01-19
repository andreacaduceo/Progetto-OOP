package com.project.JobFinderApp.util;

import com.project.JobFinderApp.exception.DataException;
import com.project.JobFinderApp.exception.CityException;
import com.project.JobFinderApp.exception.ParamException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.Vector;

/**
 * Classe che si occupa del calcolo delle statistiche.
 */

public class Statistiche {

    /**
     * Questo metodo costruisce un vettore di citta' a partire dal JSONObject per poi provvedere al calcolo delle statistiche
     * per le singole citta', infine inserisce le statistiche individuali in un JSONArray da restituire all'utente.
     *
     * @param bodyCitta JSONObject contenente le citta' delle quali si vuole conoscere le statistiche.
     * @param response Response di FindWork dopo aver estratto i campi d'interesse.
     * @return JSONArray contenente le statistiche delle singole citta'.
     */
    private JSONArray getStats(JSONObject bodyCitta, JSONArray response) {
        JSONArray result;
        Statistiche stats = new Statistiche();
        Vector<String> nomiCitta = new Vector<>();
        String nomiCittaDaEstrarre = (String) bodyCitta.get("Nomi delle città");
        String[] resultArray = nomiCittaDaEstrarre.split(",");
        nomiCitta.addAll(Arrays.asList(resultArray));
        result = stats.calcolaStatistiche(nomiCitta, response);
        return result;
    }


    /**
     * Questo metodo provvede al calcolo delle statistiche per ogni citta' presente in nomiCitta, per poi aggiungerle in
     * un JSONArray da restituire all'utente.
     * @param nomiCitta JSONObject contenente le citta' delle quali si vuole conoscere le statistiche.
     * @param response Response di FindWork dopo aver estratto i campi d'interesse.
     * @return JSONArray contenente le statistiche delle singole citta'.
     */

    private JSONArray calcolaStatistiche(Vector<String> nomiCitta, JSONArray response) {
        JSONArray result = new JSONArray();
        int indiceCitta = 0;
        for (String x : nomiCitta) {
            JSONObject objResult = new JSONObject();
            int partTimeCounter = 0;
            int fullTimeCounter = 0;
            float partTimePercent;
            float fullTimePercent;
            Vector<String> source = new Vector<>();
            Vector<String> linguaggi = new Vector<>();
            for (Object ob : response) {
                JSONObject castedOb = (JSONObject) ob;
                String currentLoc = (String) castedOb.get("Località");
                if (currentLoc.contains(x)) {
                    JSONArray app;
                    if (castedOb.get("Tipo di contratto") == "full time") fullTimeCounter++;
                    else partTimeCounter++;
                    String sourceAPI = (String) castedOb.get("Source");
                    if (!source.contains(sourceAPI)) source.add(sourceAPI);
                    app = (JSONArray) castedOb.get("Linguaggi");
                    for (Object o : app) {
                        String str = (String) o;
                        if (!linguaggi.contains(str)) linguaggi.add(str);
                    }
                }
            }
            if (partTimeCounter + fullTimeCounter != 0) {
                partTimePercent = ((float) partTimeCounter * 100 / (partTimeCounter + fullTimeCounter));
                fullTimePercent = ((float) fullTimeCounter * 100 / (partTimeCounter + fullTimeCounter));
                objResult.put("Lavori part time Quantità/Percentuale)", partTimeCounter + " / " + partTimePercent + "%");
                objResult.put("Lavori full time (Quantità/Percentuale)", fullTimeCounter + " / " + fullTimePercent + "%");
            } else {
                objResult.put("Lavori part time (Quantità/Percentuale)", partTimeCounter + " / Percentuale non disponibile");
                objResult.put("Lavori full time (Quantità/Percentuale)", fullTimeCounter + " / Percentuale non disponibile");
            }
            objResult.put("Località", nomiCitta.get(indiceCitta));
            objResult.put("Source disponibili", source.toString());
            objResult.put("Numero di linguaggi disponibili", linguaggi.size());
            objResult.put("Linguaggi disponibili", linguaggi.toString());
            result.add(objResult);
            indiceCitta++;
        }
        return result;
    }


    /**
     * Questo metodo viene richiamato per fornire delle statistiche generali delle citta' inserite.
     *
     * @param bodyCitta JSONObject contenente le citta' delle quali si vuole conoscere le statistiche.
     * @return JSONArray contenente le statistiche generali delle singole citta'.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */
    public JSONArray statisticheGenerali(JSONObject bodyCitta) throws CityException {
        JSONArray response;
        JobFinderAPI finderAPI = new JobFinderAPI();
        response = finderAPI.estraiValori(finderAPI.getMoreLocations(bodyCitta));
        return getStats(bodyCitta, response);
    }

    /**
     * Questo metodo viene richiamato per fornire delle statistiche filtrate all'utente, in particolare applica il filtro
     * per source alla response prima di passarla al calcolo delle statistiche.
     *
     * @param bodyCitta JSONObject contenente le citta' delle quali si vuole conoscere le statistiche.
     * @param source Sorgente dell'annuncio da filtrare.
     * @return JSONArray contenente le statistiche filtrate per sorgente dell'annuncio delle singole citta'.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheFiltratePerSource(JSONObject bodyCitta, String source) throws ParamException, CityException {
        JSONArray response;
        Filtri filter = new Filtri();
        response = filter.filtraPerSource(bodyCitta, source);
        return getStats(bodyCitta, response);
    }

    /** Questo metodo viene richiamato per fornire delle statistiche filtrate all'utente, in particolare applica il filtro
     * per data alla response prima di passarla al calcolo delle statistiche.
     *
     * @param bodyCitta JSONObject contenente le citta' delle quali si vuole conoscere le statistiche.
     * @param data Data di pubblicazione dell'anuncio da filtrare.
     * @return JSONArray contenente le statistiche filtrate per data dell'annuncio delle singole citta'.
     * @throws DataException Eccezione che viene lanciata quando il formato della data non rispetta: yyyy-mm-dd.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheFiltratePerData(JSONObject bodyCitta, String data) throws DataException, ParamException, CityException {
        JSONArray response;
        Filtri filter = new Filtri();
        response = filter.filtraPerData(bodyCitta, data);
        return getStats(bodyCitta, response);
    }

    /** Questo metodo viene richiamato per fornire delle statistiche filtrate all'utente, in particolare applica il filtro
     * per remoto alla response prima di passarla al calcolo delle statistiche.
     *
     * @param citta JSONObject contenente le citta' delle quali si vuole conoscere le statistiche.
     * @param remoto Stringa relativa alla disponibilita', o meno, al lavoro da remoto, che andra' a fare da filtro per la response.
     * @return JSONArray contenente le statistiche filtrate per possibilita' di lavoro da remoto delle singole citta'.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheFiltratePerRemoto(JSONObject citta, String remoto) throws ParamException, CityException {
        JSONArray response;
        Filtri filter = new Filtri();
        response = filter.filtraPerRemoto(citta, remoto);
        return getStats(citta, response);
    }

}