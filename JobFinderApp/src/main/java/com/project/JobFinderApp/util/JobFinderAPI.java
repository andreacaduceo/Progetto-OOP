package com.project.JobFinderApp.util;


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

public class JobFinderAPI {

    BufferedReader buffer;

    public JSONObject getJobs(URL url) throws IOException, ParseException {
        String result;
        StringBuilder sb = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        String token = "Token dd38bfa287efe1e654279eeded4b3d1bfa0d2c4a";
        con.addRequestProperty("Authorization", token);
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Accept","application/json");
        con.setDoOutput(true);
        buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = buffer.readLine()) != null) sb.append(line);
        JSONParser parser = new JSONParser();
        result = sb.toString();
        return (JSONObject) parser.parse(result);
    }

    public JSONObject getJobsLocation(String location) throws IOException, ParseException {
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


    public JSONArray getMoreLocations (JSONObject citta) throws IOException, ParseException {
        JobFinderAPI finderAPI = new JobFinderAPI();
        JSONArray result = new JSONArray();
        Vector<String> nomiCitta = new Vector<>();
        String nomiCittaDaEstrarre = (String) citta.get("nomiCitta");
        String[] resultArray = nomiCittaDaEstrarre.split(",");
        nomiCitta.addAll(Arrays.asList(resultArray));
        for(String x : nomiCitta) {
            result.add(finderAPI.getJobsLocation(x));
        }
        return result;
    }

    public JSONArray estraiValori (JSONArray arrayCitta) {
        JSONArray results = new JSONArray();
        for (Object value : arrayCitta) {
            JSONObject appObj = (JSONObject) value;
            JSONArray appArr = (JSONArray) appObj.get("results");
            for (Object o : appArr) {
                JSONObject objFiltrato = new JSONObject();
                JSONObject appObj2 = (JSONObject) o;
                objFiltrato.put("Nome compagnia", appObj2.get("company_name"));
                if(appObj2.get("employment_type")==null) objFiltrato.put("Tipo di contratto", "part time");
                else objFiltrato.put("Tipo di contratto", appObj2.get("employment_type"));
                objFiltrato.put("Città",appObj2.get("location"));
                objFiltrato.put("Source",appObj2.get("source"));
                objFiltrato.put("Data annuncio",appObj2.get("date_posted"));
                objFiltrato.put("Linguaggi", appObj2.get("keywords"));
                results.add(objFiltrato);
            }
        }
        return results;
    }

    public JSONObject suggerisciLocation () throws IOException, ParseException {
        JobFinderAPI api = new JobFinderAPI();
        JSONObject obj = new JSONObject();
        Vector<String> location = new Vector<>();
        location.add("London");
        location.add("New York");
        location.add("Madrid");
        location.add("Paris");
        location.add("Boston");
        long numeroLavori = 0;
        obj.put("Città suggerite",location);
        for (String s: location) {
            JSONObject app = api.getJobsLocation(s);
            numeroLavori += (long) app.get("count");
        }
        obj.put("Numero di lavori in queste città",numeroLavori);
        return obj;
    }
}
