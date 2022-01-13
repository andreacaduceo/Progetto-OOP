package com.project.JobFinderApp.util;

import com.project.JobFinderApp.exception.DataException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.Vector;

public class Statistiche {

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


    public JSONArray calcolaStatistiche(Vector<String> nomiCitta, JSONArray response) {
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
            } else {
                partTimePercent = 0;
                fullTimePercent = 0;
            }
            objResult.put("Località", nomiCitta.get(indiceCitta));
            objResult.put("Lavori part time(Quantità/Percentuale)", partTimeCounter + " / " + partTimePercent + "%");
            objResult.put("Lavori full time(Quantità/Percentuale)", fullTimeCounter + " / " + fullTimePercent + "%");
            objResult.put("Source disponibili", source.toString());
            objResult.put("Numero di linguaggi disponibili", linguaggi.size());
            objResult.put("Linguaggi disponibili", linguaggi.toString());
            result.add(objResult);
            indiceCitta++;
        }
        return result;
    }


    public JSONArray statisticheGenerali(JSONObject bodyCitta) {
        JSONArray response;
        JobFinderAPI finderAPI = new JobFinderAPI();
        response = finderAPI.estraiValori(finderAPI.getMoreLocations(bodyCitta));
        return getStats(bodyCitta, response);
    }


    public JSONArray statisticheFiltratePerSource(JSONObject bodyCitta, String source) {
        JSONArray response;
        Filtri filter = new Filtri();
        response = filter.filtraPerSource(bodyCitta, source);
        return getStats(bodyCitta, response);
    }


    public JSONArray statisticheFiltratePerData(JSONObject bodyCitta, String data) throws DataException {
        JSONArray response;
        Filtri filter = new Filtri();
        response = filter.filtraPerData(bodyCitta, data);
        return getStats(bodyCitta, response);
    }


    public JSONArray statisticheFiltratePerRemoto(JSONObject citta, String remoto) {
        JSONArray response;
        Filtri filter = new Filtri();
        response = filter.filtraPerRemoto(citta, remoto);
        return getStats(citta, response);
    }

}