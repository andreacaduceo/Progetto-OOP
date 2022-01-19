package com.project.JobFinderApp.util;


import com.project.JobFinderApp.exception.CityException;
import com.project.JobFinderApp.exception.DataException;
import com.project.JobFinderApp.exception.ParamException;
import com.project.JobFinderApp.model.JobInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Vector;

/**
 * Classe che si occupa della gestione delle chiamate a FindWork e di lavorare la response ottenuta, filtrandola o
 * calcolando delle statistiche.
 */

public class JobFinderAPI {

    private BufferedReader buffer;

    /**
     * Questo metodo si occupa di stabilire una connessione con l'url passato, dopo di che tramite BufferedReader procede
     * a immagazzinare la risposta in un JSONObject.
     *
     * @param url URL del sito al quale si vuole fare la chiamata.
     * @return JSONObject contenente la risposta della chiamata API.
     * @throws IOException Eccezione lanciata qualora ci siano problemi d'input/output.
     * @throws ParseException Eccezione che viene lanciata qualora si verifichino problemi con il parsing a JSONObject.
     */

    private JSONObject getJobs(URL url) throws IOException, ParseException {
        String result;
        StringBuilder sb = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        String token = "Token dd38bfa287efe1e654279eeded4b3d1bfa0d2c4a";
        con.addRequestProperty("Authorization", token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = buffer.readLine()) != null) sb.append(line);
        JSONParser parser = new JSONParser();
        result = sb.toString();
        return (JSONObject) parser.parse(result);
    }

    /**
     * Questo metodo consente di fare una chiamata API con parametro a FindWork, restituendo i lavori presenti nella citta'
     * inserita come parametro.
     *
     * @param location La citta' della quale si vogliono conoscere i lavori disponibili.
     * @return JSONObject dei lavori diponibili per la citta' scelta.
     * @throws IOException Eccezione lanciata qualora ci siano problemi d'input/output.
     * @throws ParseException Eccezione che viene lanciata qualora si verifichino problemi con il parsing a JSONObject.
     */

    private JSONObject getJobsLocation(String location) throws IOException, ParseException {
        String urlString = "https://findwork.dev/api/jobs/?search=&source=&location=" + location;
        URL url = new URL(urlString);
        JobFinderAPI finderAPI = new JobFinderAPI();
        String nextPage;
        JSONObject obj = finderAPI.getJobs(url);
        nextPage = (String) obj.get("next");
        if (nextPage != null) {
            URL newUrl = new URL(nextPage);
            String objString = obj.toString();
            StringBuilder sb = new StringBuilder();
            sb.append(objString);
            while (nextPage != null) {
                JSONObject appObj = finderAPI.getJobs(newUrl);
                nextPage = (String) appObj.get("next");
                newUrl = new URL(nextPage);
                sb.append(appObj);
            }
            String result = sb.toString();
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(result);
        }
        return obj;
    }

    /**
     * Questo metodo consente di effettuare piu' chiamate a FindWork, una per ogni citta' presente nel JSONObject citta,
     * provvede poi ad aggiungere tutti i JSONObject di riposta in un JSONArray con i lavori di tutte le citta'.
     *
     * @param citta JSONObject con chiave "Nomi delle città" e valore "NomiDelleCittaSeparateDallaVirgola".
     * @return JSONArray contenente i lavori disponibli in tutte le citta' presenti nel JSONObject citta.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray getMoreLocations(JSONObject citta) throws CityException {
        if(citta.get("Nomi delle città").toString().isEmpty()) throw new CityException();
        JobFinderAPI finderAPI = new JobFinderAPI();
        JSONArray result = new JSONArray();
        Vector<String> nomiCitta = new Vector<>();
        String nomiCittaDaEstrarre = (String) citta.get("Nomi delle città");
        String[] resultArray = nomiCittaDaEstrarre.split(",");
        nomiCitta.addAll(Arrays.asList(resultArray));
        for (String x : nomiCitta) {
            try {
                result.add(finderAPI.getJobsLocation(x));
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        JSONObject obCheck = (JSONObject) result.get(0);
        if((long) obCheck.get("count") == 0)throw new CityException();
        return result;
    }

    /**
     * Questo metodo si occupa di estrarre solo i campi d'interesse dalla response di FindWork.
     *
     * @param response Response ottenuta da FindWork.
     * @return JSONArray contenente i campi d'interesse dei lavori disponibili.
     */

    public JSONArray estraiValori(JSONArray response) {
        JSONArray results = new JSONArray();
        for (Object value : response) {
            JSONObject appObj = (JSONObject) value;
            JSONArray appArr = (JSONArray) appObj.get("results");
            for (Object o : appArr) {
                JSONObject appObj2 = (JSONObject) o;
                String nomeCompagnia = (String) appObj2.get("company_name");
                String tipoContratto;
                String citta = (String) appObj2.get("location");
                String source = (String) appObj2.get("source");
                String data = (String) appObj2.get("date_posted");
                //Serve per convertire il formato della data da: yyyy-MM-ddThh:mm:ssZ a yyyy-mm-dd
                data = data.substring(0, 10);
                String remoto;
                if ((boolean) appObj2.get("remote")) remoto = "Disponibile";
                else remoto = "Non disponibile";
                JSONArray linguaggi = (JSONArray) appObj2.get("keywords");
                if (appObj2.get("employment_type") == null) tipoContratto = "part time";
                else tipoContratto = (String) appObj2.get("employment_type");
                if (tipoContratto.equals("contract")) tipoContratto = "part time";
                JobInfo jobInfo = new JobInfo(nomeCompagnia, tipoContratto, citta, data, source, remoto, linguaggi);
                results.add(jobInfo.toJSONObject());
            }
        }
        return results;
    }

    /**
     * Questo metodo si occupa di costruire un JSONObject al cui interno ci sia un vettore di citta' preimpostato e il
     * numero di lavori attualmente presenti in tali citta'.
     *
     * @return JSONObject con un set di cinque citta' preimpostato e il numero di lavori disponibili.
     * @throws IOException Eccezione lanciata qualora ci siano problemi d'input/output.
     * @throws ParseException Eccezione che viene lanciata qualora ci siano problemi di parsing.
     */

    public JSONObject suggerisciLocation() throws IOException, ParseException {
        JobFinderAPI api = new JobFinderAPI();
        JSONObject obj = new JSONObject();
        Vector<String> location = new Vector<>();
        location.add("London");
        location.add("Copenaghen");
        location.add("Madrid");
        location.add("Paris");
        location.add("Amsterdam");
        long numeroLavori = 0;
        obj.put("Città suggerite", location);
        for (String s : location) {
            JSONObject app = api.getJobsLocation(s);
            numeroLavori += (long) app.get("count");
        }
        obj.put("Numero di lavori in queste città", numeroLavori);
        return obj;
    }

    /**
     * Questo metodo si occupa di effettuare il filtraggio per tipologia di contratto.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param contratto Stringa relativa alla tipologia di contratto.
     * @return JSONArray filtrato per tipologia di contratto.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray filteredByContract(JSONObject citta, String contratto) throws ParamException, CityException {
        JSONArray array;
        Filtri filter = new Filtri();
        array = filter.filtraPerContratto(citta, contratto);
        return array;
    }

    /**
     * Questo metodo si occupa di effettuare il filtraggio per sorgente di annuncio.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param source Stringa relativa alla tipologia di contratto.
     * @return JSONArray filtrato per sorgente di annuncio.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray filteredBySource(JSONObject citta, String source) throws ParamException, CityException {
        JSONArray array;
        Filtri filter = new Filtri();
        array = filter.filtraPerSource(citta, source);
        return array;
    }

    /**
     * Questo metodo si occupa di effettuare il filtraggio per linguaggio di programmazione.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param linguaggio Stringa relativa alla tipologia di contratto.
     * @return JSONArray filtrato per linguaggio di programmazione.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray filteredByLanguage(JSONObject citta, String linguaggio) throws ParamException, CityException {
        JSONArray array;
        Filtri filter = new Filtri();
        array = filter.filtraPerLinguaggio(citta, linguaggio);
        return array;
    }

    /**
     * Questo metodo si occupa di restituire delle statistiche generali relative alle citta' inserite.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @return JSONArray contenete le statistiche riguardanti le citta' scelte.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheGenerali(JSONObject citta) throws CityException {
        JSONArray array;
        Statistiche stats = new Statistiche();
        array = stats.statisticheGenerali(citta);
        return array;
    }

    /**
     * Questo metodo si occupa di restituire delle statistiche filtrate per sorgente relative alle citta' inserite.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param source Sorgente dell'annuncio che andra' a filtrare le statistiche.
     * @return JSONArray contenente le statistiche riguardanti i lavori filtrati per sorgente delle citta' inserite.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheFiltratePerSource(JSONObject citta, String source) throws ParamException, CityException {
        JSONArray array;
        Statistiche stats = new Statistiche();
        array = stats.statisticheFiltratePerSource(citta, source);
        return array;
    }

    /**
     * Questo metodo si occupa di restituire delle statistiche filtrate per data dell'annuncio relative alle citta' inserite.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param data Data dell'annuncio che andra' a filtrare le statistiche, con formato: yyyy-mm-dd.
     * @return JSONArray contenente le statistiche riguardanti i lavori filtrati per data dell'annuncio.
     * @throws DataException Eccezione lanciata qualora il formato della data non sia: yyyy-mm-dd.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */
    public JSONArray statisticheFiltratePerData(JSONObject citta, String data) throws DataException, ParamException, CityException {
        JSONArray array;
        Statistiche stats = new Statistiche();
        array = stats.statisticheFiltratePerData(citta, data);
        return array;
    }

    /**
     * Questo metodo si occupa di restituire delle statistiche filtrate per disponibilita' di lavoro da remoto relative
     * alle citta' inserite.
     *
     * @param citta JSONObject contenente le citta' inserite dall'utente.
     * @param remoto Disponibilita' per lavoro da remoto che andra' a filtrare le statistiche.
     * @return JSONArray contenente le statistiche riguardanti i lavori filtrati per possibilita' di lavoro da remoto
     *         delle citta' inserite.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray statisticheFiltratePerRemoto(JSONObject citta, String remoto) throws ParamException, CityException {
        JSONArray array;
        Statistiche stats = new Statistiche();
        array = stats.statisticheFiltratePerRemoto(citta, remoto);
        return array;
    }
}
