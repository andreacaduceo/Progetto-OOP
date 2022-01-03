package com.project.JobFinderApp.service;

import com.project.JobFinderApp.util.JobFinderAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class Service {

    JobFinderAPI api = new JobFinderAPI();

    public JSONObject chiamataLocation(String localita) throws IOException, ParseException {
        JSONObject objectResult = api.getJobsLocation(localita);
        return objectResult;
    }

    public JSONArray chiamataPiuLocation(JSONObject nomiCitta) throws IOException, ParseException {
        JSONArray arrayResult = api.estraiValori(api.getMoreLocations(nomiCitta));
        return arrayResult;
    }
}
