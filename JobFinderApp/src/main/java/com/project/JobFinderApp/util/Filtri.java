package com.project.JobFinderApp.util;


import com.project.JobFinderApp.exception.DataException;
import com.project.JobFinderApp.exception.ParamException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Filtri {

    public JSONArray filtraPerContratto(JSONObject bodyCitta, String contratto) throws ParamException {
        if(contratto.isBlank()) throw new ParamException();
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

    public JSONArray filtraPerSource(JSONObject bodyCitta, String source) throws ParamException {
        if(source.isBlank()) throw  new ParamException();
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

    public JSONArray filtraPerLinguaggio(JSONObject bodyCitta, String linguaggio) throws ParamException {
        if(linguaggio.isBlank()) throw new ParamException();
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

    public JSONArray filtraPerData(JSONObject bodyCitta, String data) throws DataException, ParamException {
        if(data.isBlank()) throw new ParamException();
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

    public JSONArray filtraPerRemoto(JSONObject bodyCitta, String remoto) throws ParamException {
        if(remoto.isBlank()) throw new ParamException();
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
