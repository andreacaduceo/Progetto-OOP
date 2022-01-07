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
        for (Object ob: response) {
            JSONObject object = (JSONObject) ob;
            String employmentType = (String) object.get("Tipo di contratto");
            if(employmentType.equalsIgnoreCase(contratto)) result.add(object);
        }
        return result;
    }

    public JSONArray filtraPerSource(JSONObject bodyCittà, String source) throws IOException, ParseException {
        JobFinderAPI api = new JobFinderAPI();
        JSONArray result = new JSONArray();
        JSONArray response = api.estraiValori(api.getMoreLocations(bodyCittà));
        for (Object ob: response) {
            JSONObject object = (JSONObject) ob;
            String sourceAPI = (String) object.get("Source");
            if(sourceAPI.equalsIgnoreCase(source)) result.add(object);
        }
        return result;
    }
}
