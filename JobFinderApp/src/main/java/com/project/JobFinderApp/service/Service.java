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


@Component
public class Service {

    JobFinderAPI api = new JobFinderAPI();

    public JSONArray chiamataPiuLocation(JSONObject nomiCitta) throws CityException {
        return api.estraiValori(api.getMoreLocations(nomiCitta));
    }

    public JSONObject suggerisciLocation() throws IOException, ParseException {
        return api.suggerisciLocation();
    }

    public JSONArray chiamataFiltrataPerContratto(JSONObject citta, String contratto) throws ParamException, CityException {
        return api.filteredByContract(citta, contratto);
    }

    public JSONArray chiamataFiltrataPerSource(JSONObject citta, String source) throws ParamException, CityException {
        return api.filteredBySource(citta, source);
    }

    public JSONArray chiamataFiltrataPerLinguaggio(JSONObject citta, String linguaggio) throws ParamException, CityException {
        return api.filteredByLanguage(citta, linguaggio);
    }

    public JSONArray statisticheGenerali(JSONObject citta) throws CityException {
        return api.statisticheGenerali(citta);
    }

    public JSONArray statisticheSource(JSONObject citta, String source) throws ParamException, CityException {
        return api.statisticheFiltratePerSource(citta, source);
    }

    public JSONArray statisticheData(JSONObject citta, String data) throws DataException, ParamException, CityException {
        return api.statisticheFiltratePerData(citta, data);
    }

    public JSONArray statisticheRemoto(JSONObject citta, String remoto) throws ParamException, CityException {
        return api.statisticheFiltratePerRemoto(citta, remoto);
    }
}
