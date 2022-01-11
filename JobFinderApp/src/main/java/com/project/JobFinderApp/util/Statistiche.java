package com.project.JobFinderApp.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class Statistiche {

    public  JSONArray calcolaStatistiche(Vector<String> nomiCittà, JSONArray response) {
        JSONArray result = new JSONArray();
        int indiceCittà = 0;
        for (String x: nomiCittà) {
            JSONObject objResult = new JSONObject();
            int partTimeCounter = 0;
            int fullTimeCounter = 0;
            float partTimePercent;
            float fullTimePercent;
            Vector<String> source = new Vector<>();
            Vector<String> linguaggi = new Vector<>();
            for (Object ob: response) {
                JSONObject castedOb = (JSONObject) ob;
                String currentLoc = (String) castedOb.get("Località");
                if(currentLoc.contains(x)) {
                    JSONArray app;
                    if(castedOb.get("Tipo di contratto")=="full time") fullTimeCounter++;
                    else  partTimeCounter++;
                    String sourceAPI =(String) castedOb.get("Source");
                    if(!source.contains(sourceAPI)) source.add(sourceAPI);
                    app = (JSONArray) castedOb.get("Linguaggi");
                    for (Object o : app ) {
                        String str = (String) o;
                        if(!linguaggi.contains(str)) linguaggi.add(str);
                    }
                }
            }
            partTimePercent = ((float) partTimeCounter*100 / (partTimeCounter + fullTimeCounter));
            fullTimePercent = ((float) fullTimeCounter*100 / (partTimeCounter + fullTimeCounter));
            objResult.put("Località",nomiCittà.get(indiceCittà));
            objResult.put("Lavori part time(Quantità/Percentuale)",partTimeCounter + " / " + partTimePercent + "%");
            objResult.put("Lavori full time(Quantità/Percentuale)", fullTimeCounter + " / " + fullTimePercent + "%");
            objResult.put("Source disponibili", source.toString());
            objResult.put("Numero di linguaggi disponibili", linguaggi.size());
            objResult.put("Linguaggi disponibili", linguaggi.toString());
            result.add(objResult);
            indiceCittà++;
        }
        return result;
    }


    public JSONArray statisticheGenerali(JSONObject bodyCittà) throws IOException, ParseException {
        JSONArray result, response;
        JobFinderAPI finderAPI = new JobFinderAPI();
        response = finderAPI.estraiValori(finderAPI.getMoreLocations(bodyCittà));
        Statistiche stats = new Statistiche();
        Vector<String> nomiCittà = new Vector<>();
        String nomiCittaDaEstrarre = (String) bodyCittà.get("Nomi delle città");
        String[] resultArray = nomiCittaDaEstrarre.split(",");
        nomiCittà.addAll(Arrays.asList(resultArray));
        result = stats.calcolaStatistiche(nomiCittà, response);
        return result;
    }

    public JSONArray statisticheFiltratePerSource(JSONObject bodyCittà, String source) throws IOException, ParseException {
        JSONArray result, response;
        Filtri filter = new Filtri();
        response = filter.filtraPerSource(bodyCittà, source);
        Statistiche stats = new Statistiche();
        Vector<String> nomiCittà = new Vector<>();
        String nomiCittaDaEstrarre = (String) bodyCittà.get("Nomi delle città");
        String[] resultArray = nomiCittaDaEstrarre.split(",");
        nomiCittà.addAll(Arrays.asList(resultArray));
        result = stats.calcolaStatistiche(nomiCittà, response);
        return result;
    }
}