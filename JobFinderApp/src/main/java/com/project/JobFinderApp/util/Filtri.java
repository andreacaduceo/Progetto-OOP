package com.project.JobFinderApp.util;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Filtri {

    public JSONArray filtraPerContratto(JSONObject bodyCittà, String contratto) throws IOException, ParseException {
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCittà));
        for (Object ob : response) {
            JSONObject object = (JSONObject) ob;
            String employmentType = (String) object.get("Tipo di contratto");
            if (employmentType.equalsIgnoreCase(contratto)) result.add(object);
        }
        return result;
    }

    public JSONArray filtraPerSource(JSONObject bodyCittà, String source) throws IOException, ParseException {
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCittà));
        for (Object ob : response) {
            JSONObject object = (JSONObject) ob;
            String sourceAPI = (String) object.get("Source");
            if (sourceAPI.equalsIgnoreCase(source)) result.add(object);
        }
        return result;
    }

    public JSONArray filtraPerLinguaggio(JSONObject bodyCittà, String linguaggio) throws IOException, ParseException {
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCittà));
        for (Object ob : response) {
            JSONObject object = (JSONObject) ob;
            JSONArray linguaggiAPI = (JSONArray) object.get("Linguaggi");
            for (Object o : linguaggiAPI) {
                if (o.toString().equalsIgnoreCase(linguaggio)) result.add(object);

            }
        }
        return result;
    }

    public JSONArray filtraPerData(JSONObject bodyCittà, String data) throws IOException, ParseException {
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCittà));
        for (Object ob : response) {
            JSONObject object = (JSONObject) ob;
            String dataAPI = (String) object.get("Data annuncio");
            if (dataAPI.equals(data)) result.add(object);
        }
        return result;
    }

    public JSONArray filtraPerRemoto(JSONObject bodyCittà, String remoto) throws IOException, ParseException {
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCittà));
        for (Object o : response) {
            JSONObject castedOb = (JSONObject) o;
            String remotoAPI = (String) castedOb.get("Possibilità di lavoro da remoto");
            if (remotoAPI.equals(remoto)) result.add(castedOb);
        }
        return result;
    }
}
