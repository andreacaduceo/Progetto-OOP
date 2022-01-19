package com.project.JobFinderApp.util;


import com.project.JobFinderApp.exception.DataException;
import com.project.JobFinderApp.exception.CityException;
import com.project.JobFinderApp.exception.ParamException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe che si occupa del filtraggio della response di FindWork, i filtri disponibli sono: tipologia di contratto, sorgente
 * dell'annuncio, linguaggio di programmazione disponibile, data dell'annuncio e possibilita' di lavoro da remoto.
 */

public class Filtri {

    /**
     * Questo metodo si occupa del filtraggio della response per tipologia di contratto.
     *
     * @param bodyCitta JSONObject contenente le citta' inserite dall'utente.
     * @param contratto Stringa relativa alla tipologia di contratto scelta.
     * @return JSONArray contenente solo lavori con il tipo di contratto indicato.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray filtraPerContratto(JSONObject bodyCitta, String contratto) throws ParamException, CityException {
        if(contratto.isBlank()) throw new ParamException("CONTRATTO");
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCitta));
        for (Object ob : response) {
            JSONObject object = (JSONObject) ob;
            String employmentType = (String) object.get("Tipo di contratto");
            if (employmentType.equalsIgnoreCase(contratto)) result.add(object);
        }
        return result;
    }

    /**
     * Questo metodo si occupa del filtraggio della response per sorgente dell'annuncio.
     * @param bodyCitta JSONObject contenente le citta' inserite dall'utente.
     * @param source Stringa relativa alla sorgente dell'annuncio scelta.
     * @return JSONArray contenente solo lavori con sorgente uguale a quella indicata.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray filtraPerSource(JSONObject bodyCitta, String source) throws ParamException, CityException {
        if(source.isBlank()) throw  new ParamException("SOURCE");
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCitta));
        for (Object ob : response) {
            JSONObject object = (JSONObject) ob;
            String sourceAPI = (String) object.get("Source");
            if (sourceAPI.equalsIgnoreCase(source)) result.add(object);
        }
        return result;
    }

    /**
     * Questo metodo si occupa del filtraggio della response per linguaggio di programmazione.
     *
     * @param bodyCitta JSONObject contenente le citta' inserite dall'utente.
     * @param linguaggio Stringa relativa al linguaggio scelto dall'utente.
     * @return JSONArray contenente solo lavori che contengono il linguaggio di programmazione indicato.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray filtraPerLinguaggio(JSONObject bodyCitta, String linguaggio) throws ParamException, CityException {
        if(linguaggio.isBlank()) throw new ParamException("LINGUAGGIO");
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCitta));
        for (Object ob : response) {
            JSONObject object = (JSONObject) ob;
            JSONArray linguaggiAPI = (JSONArray) object.get("Linguaggi");
            for (Object o : linguaggiAPI) {
                if (o.toString().equalsIgnoreCase(linguaggio)) result.add(object);

            }
        }
        return result;
    }

    /**
     * Questo metodo si occupa del filtraggio della response per data dell'annuncio.
     *
     * @param bodyCitta JSONObject contenente le citta' inserite dall'utente.
     * @param data Stringa relativa alla data immessa dall'utente.
     * @return JSONArray contenente solo lavori pubblicati nella stessa data di quella inserita.
     * @throws DataException Eccezione che viene lanciata qualora la data non sia nel formato: yyyy-mm-dd.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray filtraPerData(JSONObject bodyCitta, String data) throws DataException, ParamException, CityException {
        if(data.isBlank()) throw new ParamException("DATA");
        //Contralla se effettivamente il formato della data è: yyyy-mm-dd
        if (data.charAt(4) != '-' || data.charAt(7) != '-') {
            throw new DataException();
        }
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCitta));
        for (Object ob : response) {
            JSONObject object = (JSONObject) ob;
            String dataAPI = (String) object.get("Data annuncio");
            if (dataAPI.equals(data)) result.add(object);
        }
        return result;
    }

    /**
     * Questo metodo si occupa del filtraggio della response per possibilita' di lavoro da remoto.
     * @param bodyCitta JSONObject contenente le citta' inserite dall'utente.
     * @param remoto Stringa contenete la disponibilita' o meno al lavoro da remoto.
     * @return JSONArray contenente solo lavori con disponibilita' di lavoro da remoto inserita.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    public JSONArray filtraPerRemoto(JSONObject bodyCitta, String remoto) throws ParamException, CityException {
        if(remoto.isBlank()) throw new ParamException("REMOTO");
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCitta));
        for (Object o : response) {
            JSONObject castedOb = (JSONObject) o;
            String remotoAPI = (String) castedOb.get("Lavoro da remoto");
            if (remotoAPI.equalsIgnoreCase(remoto)) result.add(castedOb);
        }
        return result;
    }
}
