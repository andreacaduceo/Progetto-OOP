package com.project.JobFinderApp.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class Statistiche {

    public JSONArray statisticheGenerali(JSONObject bodyCittà) throws IOException, ParseException {
        JSONArray result = new JSONArray();
        JobFinderAPI finderAPI = new JobFinderAPI();
        Vector<String> nomiCitta = new Vector<>();
        String nomiCittaDaEstrarre = (String) bodyCittà.get("Nomi delle città");
        String[] resultArray = nomiCittaDaEstrarre.split(",");
        nomiCitta.addAll(Arrays.asList(resultArray));
        int indiceCittà = 0;
        for(String x : nomiCitta) {
            JSONObject objResult = new JSONObject();
            int partTimeCounter = 0;
            int fullTimeCounter = 0;
            float partTimePercent;
            float fullTimePercent;
            Vector<String> source = new Vector<>();
            Vector<String> linguaggi = new Vector<>();
            JSONArray jobs;
            JSONArray array = new JSONArray();
            array.add(finderAPI.getJobsLocation(x));
            jobs = finderAPI.estraiValori(array);
            for (Object obj : jobs) {
                JSONObject castedObj = (JSONObject) obj;
                JSONArray app;
                if(castedObj.get("Tipo di contratto")=="part time") partTimeCounter++;
                else  fullTimeCounter++;
                String sourceAPI =(String) castedObj.get("Source");
                if(!source.contains(sourceAPI)) source.add(sourceAPI);
                app = (JSONArray) castedObj.get("Linguaggi");
                for (Object o : app ) {
                    String str = (String) o;
                    if(!linguaggi.contains(str)) linguaggi.add(str);
                }
            }
            partTimePercent = ((float) partTimeCounter*100 / (partTimeCounter + fullTimeCounter));
            fullTimePercent = ((float) fullTimeCounter*100 / (partTimeCounter + fullTimeCounter));
            objResult.put("Località",nomiCitta.get(indiceCittà));
            objResult.put("Lavori part time(Quantità/Percentuale)",partTimeCounter + " / " + partTimePercent + "%");
            objResult.put("Lavori full time(Quantità/Percentuale)", fullTimeCounter + " / " + fullTimePercent + "%");
            objResult.put("Source disponibili", source);
            objResult.put("Numero di linguaggi disponibili", linguaggi.size());
            objResult.put("Linguaggi disponibili", linguaggi);
            result.add(objResult);
            indiceCittà++;
        }
        return result;
    }
}
